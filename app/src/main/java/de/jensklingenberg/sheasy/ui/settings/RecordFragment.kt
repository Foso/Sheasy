package de.jensklingenberg.sheasy.ui.settings

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.hardware.display.DisplayManager
import android.media.MediaCodec
import android.media.MediaCodecInfo
import android.media.MediaFormat
import android.media.MediaMuxer
import android.media.projection.MediaProjection
import android.media.projection.MediaProjectionManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Base64
import android.util.Log
import android.view.Display
import android.view.Surface
import android.view.View
import androidx.annotation.RequiresApi
import de.jensklingenberg.sheasy.R
import de.jensklingenberg.sheasy.ui.common.BaseFragment
import kotlinx.android.synthetic.main.fragment_record.*
import java.io.File
import java.io.IOException
import java.nio.charset.StandardCharsets

/**
 * Created by jens on 1/4/18.
 */
class RecordFragment : BaseFragment() {
    private val TAG = "ScreenRecordActivity"
    private var mediaProjectionManager: MediaProjectionManager? = null
    private var mediaProjection: MediaProjection? = null
    private var muxer: MediaMuxer? = null
    private var inputSurface: Surface? = null
    private var videoEncoder: MediaCodec? = null
    private var muxerStarted: Boolean = false
    private var trackIndex = -1
    private var mShowStopLabel: Boolean = false
    private var mSurfaceHolderReady = false
    private val REQUEST_CODE_CAPTURE_PERM = 1234
    private val VIDEO_MIME_TYPE = "video/avc"
    private var encoderCallback: android.media.MediaCodec.Callback? = null


    override fun getLayoutId() = R.layout.fragment_record

    companion object {
        @JvmStatic
        fun newInstance() = RecordFragment()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // Populate file-selection spinner.
        // Need to create one of these fancy ArrayAdapter thingies, and specify the generic layout
        // for the widget itself.


        updateControls()


        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            AlertDialog.Builder(context)
                .setTitle("Error")
                .setMessage("This activity only works on Marshmallow or later.")
                .setNeutralButton(
                    android.R.string.ok
                ) { dialog, which -> activity?.finish() }
                .show()
            return
        }

        screen_record_button.setOnClickListener { v ->
            if (v.id == R.id.screen_record_button) {
                if (muxerStarted) {
                    stopRecording()
                    screen_record_button.text = "ON"
                } else {
                    val permissionIntent = mediaProjectionManager!!.createScreenCaptureIntent()
                    startActivityForResult(permissionIntent, REQUEST_CODE_CAPTURE_PERM)
                    screen_record_button.isEnabled = false
                }
            }
        }

        mediaProjectionManager = context?.getSystemService(
            android.content.Context.MEDIA_PROJECTION_SERVICE
        ) as MediaProjectionManager?

        encoderCallback = object : MediaCodec.Callback() {
            override fun onInputBufferAvailable(codec: MediaCodec, index: Int) {
                Log.d(TAG, "Input Buffer Avail")
            }

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
                    if (muxerStarted) {
                        val b = ByteArray(info.size)
                        encodedData.position(info.offset)
                        encodedData.limit(info.offset + info.size)
                        encodedData[b, info.offset, info.offset + info.size]
                        Log.d("this", encodedData.toString())
                        Log.d("this", b.toString())
                        val infoString = info.offset.toString() + "," + info.size + "," +
                                info.presentationTimeUs + "," + info.flags


                        val s = StandardCharsets.UTF_8.decode(encodedData).toString()
                        val response =
                            Base64.encodeToString(b, Base64.DEFAULT)
                        Log.d("THIS", response)



                        muxer?.writeSampleData(trackIndex, encodedData, info)
                    }
                }

                videoEncoder?.releaseOutputBuffer(index, false)

            }

            override fun onError(codec: MediaCodec, e: MediaCodec.CodecException) {
                Log.e(TAG, "MediaCodec " + codec.name + " onError:", e)
            }

            override fun onOutputFormatChanged(codec: MediaCodec, format: MediaFormat) {
                Log.d(TAG, "Output Format changed")
                if (trackIndex >= 0) {
                    throw RuntimeException("format changed twice")
                }
                trackIndex = muxer!!.addTrack(videoEncoder!!.outputFormat)
                if (!muxerStarted && trackIndex >= 0) {
                    muxer?.start()
                    muxerStarted = true
                }
            }
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

    }


    private fun updateControls() {
        if (mShowStopLabel) {
            play_stop_button.text = "R.string.stop_button_text"
        } else {
            play_stop_button.text = "R.string.play_button_text"
        }
        play_stop_button.isEnabled = mSurfaceHolderReady
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private fun startRecording() {
        val dm = context?.getSystemService(Context.DISPLAY_SERVICE) as DisplayManager?
        val defaultDisplay: Display?
        if (dm != null /* handler */) {
            defaultDisplay = dm.getDisplay(Display.DEFAULT_DISPLAY)
        } else {
            throw IllegalStateException("Cannot display manager?!?")
        }
        if (defaultDisplay == null) {
            throw RuntimeException("No display found.")
        }

        // Get the display size and density.
        val metrics = resources.displayMetrics
        val screenWidth = metrics.widthPixels
        val screenHeight = metrics.heightPixels
        val screenDensity = metrics.densityDpi

        prepareVideoEncoder(screenWidth, screenHeight)

        try {
            val outputFile = File(
                (Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES
                )).toString() + "/grafika", ("Screen-record-" +
                        java.lang.Long.toHexString(System.currentTimeMillis()) + ".mp4")
            )
            if (!outputFile.parentFile.exists()) {
                outputFile.parentFile.mkdirs()
            }
            muxer = MediaMuxer(
                outputFile.canonicalPath,
                MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4
            )
        } catch (ioe: IOException) {
            throw RuntimeException("MediaMuxer creation failed", ioe)
        }


        // Start the video input.
        mediaProjection?.createVirtualDisplay(
            "Recording Display",
            screenWidth,
            screenHeight,
            screenDensity,
            DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR/* flags */,
            inputSurface,
            null,
            null
        )/* callback */
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private fun prepareVideoEncoder(width: Int, height: Int) {
        val frameRate = 30 // 30 fps
        val format = MediaFormat.createVideoFormat(VIDEO_MIME_TYPE, width, height).apply {
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

        // Set some required properties. The media codec may fail if these aren't defined.


        // Create a MediaCodec encoder and configure it. Get a Surface we can use for recording into.
        try {

            videoEncoder = MediaCodec.createEncoderByType(VIDEO_MIME_TYPE).apply {
                configure(format, null, null, MediaCodec.CONFIGURE_FLAG_ENCODE)
                setCallback(encoderCallback)

            }

            inputSurface = videoEncoder!!.createInputSurface()
            videoEncoder?.start()

        } catch (e: IOException) {
            releaseEncoders()
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private fun releaseEncoders() {
        if (muxer != null) {
            if (muxerStarted) {
                muxer!!.stop()
            }
            muxer!!.release()
            muxer = null
            muxerStarted = false
        }
        if (videoEncoder != null) {
            videoEncoder!!.stop()
            videoEncoder!!.release()
            videoEncoder = null
        }
        if (inputSurface != null) {
            inputSurface!!.release()
            inputSurface = null
        }
        if (mediaProjection != null) {
            mediaProjection!!.stop()
            mediaProjection = null
        }
        trackIndex = -1
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private fun stopRecording() {
        releaseEncoders()
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent) {
        if (REQUEST_CODE_CAPTURE_PERM == requestCode) {
            val b = screen_record_button
            b.isEnabled = true
            if (resultCode == Activity.RESULT_OK) {
                mediaProjection = mediaProjectionManager?.getMediaProjection(resultCode, intent)
                startRecording()
                b.text = "OFF"
            } else {
                // user did not grant permissions
                AlertDialog.Builder(context)
                    .setTitle("Error")
                    .setMessage("Permission is required to record the screen.")
                    .setNeutralButton(android.R.string.ok, null)
                    .show()
            }
        }
    }


}
