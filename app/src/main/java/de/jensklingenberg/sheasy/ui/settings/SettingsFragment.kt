package de.jensklingenberg.sheasy.ui.settings

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.hardware.display.DisplayManager
import android.hardware.display.VirtualDisplay
import android.media.MediaCodec
import android.media.MediaCodecInfo
import android.media.MediaFormat
import android.media.projection.MediaProjection
import android.media.projection.MediaProjectionManager
import android.os.Bundle
import android.util.Base64
import android.util.DisplayMetrics
import android.util.Log
import android.view.Surface
import android.view.SurfaceView
import android.view.View
import de.jensklingenberg.sheasy.R
import de.jensklingenberg.sheasy.ui.common.BaseFragment
import kotlinx.android.synthetic.main.fragment_settings.*
import javax.inject.Inject


class SettingsFragment : BaseFragment(), View.OnClickListener {

    @Inject
    lateinit var mediaProjectionManager: MediaProjectionManager


    private var mScreenDensity: Int = 0

    private var mResultCode: Int = 0
    private var mResultData: Intent? = null
    private var encoderCallback: android.media.MediaCodec.Callback? = null
    private var videoEncoder: MediaCodec? = null

    private var mSurface: Surface? = null
    private var mMediaProjection: MediaProjection? = null
    private var mVirtualDisplay: VirtualDisplay? = null
    private var mMediaProjectionManager: MediaProjectionManager? = null
    private var mSurfaceView: SurfaceView? = null
    private val VIDEO_MIME_TYPE = "video/avc"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            mResultCode = savedInstanceState.getInt(STATE_RESULT_CODE)
            mResultData = savedInstanceState.getParcelable(STATE_RESULT_DATA)
        }
    }

    override fun getLayoutId() = R.layout.fragment_settings

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mSurfaceView = view.findViewById<View>(R.id.surface) as SurfaceView
        mSurface = mSurfaceView!!.holder.surface

        toggle!!.setOnClickListener(this)

        encoderCallback = object : MediaCodec.Callback() {
            override fun onOutputBufferAvailable(
                codec: MediaCodec,
                index: Int,
                info: MediaCodec.BufferInfo
            ) {
                val encodedData = videoEncoder!!.getOutputBuffer(index)
                    ?: throw RuntimeException("couldn't fetch buffer at index $index")

                if (info.flags and MediaCodec.BUFFER_FLAG_CODEC_CONFIG != 0) {
                    Log.d("THIS", "HERE")
                    //info.size = 0;
                }

                if (info.size != 0) {

                    val b = ByteArray(info.size)
                    encodedData.position(info.offset)
                    encodedData.limit(info.offset + info.size)
                    encodedData[b, info.offset, info.offset + info.size]
                    Log.d("this", encodedData.toString())
                    Log.d("this", b.toString())
                    val infoString = info.offset.toString() + "," + info.size + "," +
                            info.presentationTimeUs + "," + info.flags


                    val response =
                        Base64.encodeToString(b, Base64.DEFAULT) + ",," + infoString


                }

                videoEncoder?.releaseOutputBuffer(index, false)


            }

            override fun onInputBufferAvailable(codec: MediaCodec, index: Int) {
            }

            override fun onOutputFormatChanged(codec: MediaCodec, format: MediaFormat) {
            }

            override fun onError(codec: MediaCodec, e: MediaCodec.CodecException) {
            }

        }

        val metrics = resources.displayMetrics

        val screenWidth = metrics.widthPixels
        val screenHeight = metrics.heightPixels

        val frameRate = 30 // 30 fps
        val format =
            MediaFormat.createVideoFormat(VIDEO_MIME_TYPE, screenWidth, screenHeight).apply {
                setInteger(
                    MediaFormat.KEY_COLOR_FORMAT,
                    MediaCodecInfo.CodecCapabilities.COLOR_FormatSurface
                )
                setInteger(MediaFormat.KEY_BIT_RATE, (1024.0 * 1024.0 * 0.5).toInt())
                setInteger(MediaFormat.KEY_FRAME_RATE, frameRate)
                setInteger(MediaFormat.KEY_CAPTURE_RATE, frameRate)
                setInteger(MediaFormat.KEY_REPEAT_PREVIOUS_FRAME_AFTER, 1000000 / frameRate)
                setInteger(MediaFormat.KEY_CHANNEL_COUNT, 1)
                setInteger(MediaFormat.KEY_I_FRAME_INTERVAL, 1) // 1 seconds between I-frames
            }

        videoEncoder = MediaCodec.createEncoderByType(VIDEO_MIME_TYPE).apply {
            configure(format, null, null, MediaCodec.CONFIGURE_FLAG_ENCODE)
            setCallback(encoderCallback)

        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = activity
        val metrics = DisplayMetrics()
        activity!!.windowManager.defaultDisplay.getMetrics(metrics)
        mScreenDensity = metrics.densityDpi
        mMediaProjectionManager =
                activity.getSystemService(Context.MEDIA_PROJECTION_SERVICE) as MediaProjectionManager
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (mResultData != null) {
            outState.putInt(STATE_RESULT_CODE, mResultCode)
            outState.putParcelable(STATE_RESULT_DATA, mResultData)
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.toggle -> if (mVirtualDisplay == null) {
                startScreenCapture()
            } else {
                stopScreenCapture()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_MEDIA_PROJECTION) {
            if (resultCode != Activity.RESULT_OK) {
                Log.i(TAG, "User cancelled")
                return
            }
            val activity = activity ?: return
            Log.i(TAG, "Starting screen capture")
            mResultCode = resultCode
            mResultData = data
            mMediaProjection =
                    mMediaProjectionManager!!.getMediaProjection(mResultCode, mResultData!!)
            setUpVirtualDisplay()
        }
    }

    override fun onPause() {
        super.onPause()
        stopScreenCapture()
    }

    override fun onDestroy() {
        super.onDestroy()
        tearDownMediaProjection()
    }

    private fun tearDownMediaProjection() {
        if (mMediaProjection != null) {
            mMediaProjection!!.stop()
            mMediaProjection = null
        }
    }

    private fun startScreenCapture() {
        // Get the display size and density.
        val metrics = resources.displayMetrics
        val screenWidth = metrics.widthPixels
        val screenHeight = metrics.heightPixels
        val screenDensity = metrics.densityDpi


        val activity = activity
        if (mSurface == null || activity == null) {
            return
        }
        if (mMediaProjection != null) {
            setUpVirtualDisplay()
        } else if (mResultCode != 0 && mResultData != null) {
            mMediaProjection =
                    mMediaProjectionManager!!.getMediaProjection(mResultCode, mResultData!!)
            setUpVirtualDisplay()
        } else {
            Log.i(TAG, "Requesting confirmation")
            // This initiates a prompt dialog for the user to confirm screen projection.
            startActivityForResult(
                mMediaProjectionManager!!.createScreenCaptureIntent(),
                REQUEST_MEDIA_PROJECTION
            )
        }
    }

    private fun setUpVirtualDisplay() {
        Log.i(
            TAG, "Setting up a VirtualDisplay: " +
                    mSurfaceView!!.width + "x" + mSurfaceView!!.height +
                    " (" + mScreenDensity + ")"
        )
        mVirtualDisplay = mMediaProjection!!.createVirtualDisplay(
            "ScreenCapture",
            mSurfaceView!!.width, mSurfaceView!!.height, mScreenDensity,
            DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR,
            mSurface, null, null
        )
        toggle!!.setText(R.string.stop)
    }

    private fun stopScreenCapture() {
        if (mVirtualDisplay == null) {
            return
        }
        mVirtualDisplay!!.release()
        mVirtualDisplay = null
    }

    companion object {

        private val TAG = "ScreenCaptureFragment"

        private val STATE_RESULT_CODE = "result_code"
        private val STATE_RESULT_DATA = "result_data"

        private val REQUEST_MEDIA_PROJECTION = 1
    }


}
