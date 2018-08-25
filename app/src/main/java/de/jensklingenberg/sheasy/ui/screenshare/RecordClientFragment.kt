package de.jensklingenberg.sheasy.ui.screenshare

import android.graphics.Point
import android.media.MediaCodec
import android.media.MediaFormat
import android.media.MediaMuxer
import android.media.projection.MediaProjection
import android.media.projection.MediaProjectionManager
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.Surface
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import de.jensklingenberg.sheasy.R
import de.jensklingenberg.sheasy.ui.MainActivity
import de.jensklingenberg.sheasy.ui.common.BaseFragment
import de.jensklingenberg.sheasy.ui.common.ITabView
import de.jensklingenberg.sheasy.utils.extension.str_to_bb
import io.ktor.util.decodeString
import kotlinx.android.synthetic.main.fragment_record_client.*
import okhttp3.*
import okio.ByteString
import okhttp3.OkHttpClient
import java.io.IOException
import java.nio.ByteBuffer
import java.nio.charset.Charset


/**
 * Created by jens on 1/4/18.
 */
class RecordClientFragment : BaseFragment(), ITabView {

    private val client = OkHttpClient()

    private val TAG = "ScreenRecordActivity"

    private var firstIFrameAdded: Boolean = false
    internal var decoder: MediaCodec? = null
    internal var videoResolution = Point()
    internal var decoderConfigured = false
    internal var info2 = MediaCodec.BufferInfo()

    internal var encBuffer = CircularEncoderBuffer((1024.0 * 1024.0 * 0.5).toInt(), 60, 7)


    override fun getTabNameResId() = R.string.main_frag_tab_name


    override fun getLayoutId() = R.layout.fragment_record_client

    companion object {
        @JvmStatic
        fun newInstance() = RecordClientFragment()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        surfaceView.holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceCreated(surfaceHolder: SurfaceHolder) {
                try {
                    decoder = MediaCodec.createDecoderByType(CodecUtils.MIME_TYPE)
                    Thread(Runnable { doDecoderThingie() }).start()

                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }

            override fun surfaceChanged(surfaceHolder: SurfaceHolder, i: Int, i2: Int, i3: Int) {}

            override fun surfaceDestroyed(surfaceHolder: SurfaceHolder) {}
        })

        start()

    }


    private fun start() {

        val request = Request.Builder().url("ws://192.168.2.40:8765/screenshare").build()
        val listener = EchoWebSocketListener()
        val ws = client.newWebSocket(request, listener)

        //client.dispatcher().executorService().shutdown()
    }

    private inner class EchoWebSocketListener : WebSocketListener() {

        private val NORMAL_CLOSURE_STATUS = 1000

        override fun onOpen(webSocket: WebSocket, response: Response) {
            Log.d("THIS<<<<<<<", "onOpen")

            webSocket.send("Hello, it's SSaurel !")
            // webSocket.send("What's up ?")
            // webSocket.send(ByteString.decodeHex("deadbeef"))
            //webSocket.close(NORMAL_CLOSURE_STATUS, "Goodbye !")
        }

        override fun onMessage(webSocket: WebSocket, text: String) {
            Log.d("THIS<<<<<<<", "Receiving : $text")
            val split = text.split(",,")

            val test = Base64.decode(split[0], Base64.DEFAULT)
            val byteBuffer = ByteBuffer.wrap(test)
            val s = split[1]

            val parts = s.split(",")
            try {
                info2.set(
                    Integer.parseInt(parts[0]),
                    Integer.parseInt(parts[1]),
                    java.lang.Long.parseLong(parts[2]),
                    Integer.parseInt(parts[3])
                )
            } catch (e: NumberFormatException) {
                e.printStackTrace()
                Log.d(TAG, "===========Exception = " + e.message + " =================")
                //TODO: Need to stop the decoder or to skip the current decoder loop
                //showToast(e.getMessage());
            }

            setData(byteBuffer, info2)
            // doDecoderThingie()
        }

        override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
            Log.d("THIS<<<<<<", "Receiving bytes : " + bytes.hex())
        }

        override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
            webSocket.close(NORMAL_CLOSURE_STATUS, null)
            Log.d("THIS", "Closing : $code / $reason")
        }

        override fun onFailure(webSocket: WebSocket?, t: Throwable?, response: Response?) {
            Log.d("THIS", "Error : " + t?.message)

        }


    }

    fun doDecoderThingie() {
        val outputDone = false

        while (!decoderConfigured) {
        }

        if (MainActivity.DEBUG) Log.d(TAG, "Decoder Configured")

        while (!firstIFrameAdded) {
        }

        if (MainActivity.DEBUG) Log.d(TAG, "Main Body")

        var index = encBuffer.firstIndex
        if (index < 0) {
            Log.e(TAG, "CircularBuffer Error")
            return
        }
        var encodedFrames: ByteBuffer
        val info = MediaCodec.BufferInfo()
        while (!outputDone) {
            encodedFrames = encBuffer.getChunk(index, info)
            encodedFrames.limit(info.size + info.offset)
            encodedFrames.position(info.offset)

            try {
                index = encBuffer.getNextIntCustom(index)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }

            val inputBufIndex = decoder!!.dequeueInputBuffer(-1)
            if (inputBufIndex >= 0) {
                val inputBuf = decoder!!.getInputBuffer(inputBufIndex)
                inputBuf!!.clear()
                inputBuf.put(encodedFrames)
                decoder?.queueInputBuffer(
                    inputBufIndex, 0, info2.size,
                    info2.presentationTimeUs, info2.flags
                )
            }

            if (decoderConfigured) {
                val decoderStatus =
                    decoder!!.dequeueOutputBuffer(info, CodecUtils.TIMEOUT_USEC.toLong())
                if (decoderStatus == MediaCodec.INFO_TRY_AGAIN_LATER) {
                    if (MainActivity.DEBUG) Log.d(TAG, "no output from decoder available")
                } else if (decoderStatus == MediaCodec.INFO_OUTPUT_BUFFERS_CHANGED) {
                    if (MainActivity.DEBUG) Log.d(TAG, "decoder output buffers changed")
                } else if (decoderStatus == MediaCodec.INFO_OUTPUT_FORMAT_CHANGED) {
                    // this happens before the first frame is returned
                    val decoderOutputFormat = decoder!!.outputFormat
                    Log.d(TAG, "decoder output format changed: $decoderOutputFormat")
                } else {
                    Log.d(TAG, "DECODER RELEASE")
                    decoder!!.releaseOutputBuffer(decoderStatus, true)
                }
            }
        }
    }

    private fun setData(encodedFrame: ByteBuffer, info: MediaCodec.BufferInfo) {

        val infoString = info.offset.toString() + "," + info.size + "," +
                info.presentationTimeUs + "," + info.flags
        Log.d("this", infoString)


        //onStringAvailable(infoString)

        if (info.flags and MediaCodec.BUFFER_FLAG_CODEC_CONFIG != 0) {
            Log.d(TAG, "Configuring Decoder")
            val format = MediaFormat.createVideoFormat(
                CodecUtils.MIME_TYPE,
                CodecUtils.WIDTH,
                CodecUtils.HEIGHT
            )
            format.setByteBuffer("csd-0", encodedFrame)
            decoder!!.configure(
                format, surfaceView.holder.surface,
                null, 0
            )
            decoder!!.start()
            decoderConfigured = true
            Log.d(TAG, "decoder configured (" + info.size + " bytes)")
            return
        }

        encBuffer.add(encodedFrame, info.flags, info.presentationTimeUs)
        if (MainActivity.DEBUG) Log.d(TAG, "Adding frames to the Buffer")
        if (info.flags and MediaCodec.BUFFER_FLAG_KEY_FRAME != 0) {
            firstIFrameAdded = true
            if (MainActivity.DEBUG) Log.d(TAG, "First I-Frame added")
        }
    }

    fun onStringAvailable(s: String) {
        val parts = s.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        try {
            info2.set(
                Integer.parseInt(parts[0]),
                Integer.parseInt(parts[1]),
                java.lang.Long.parseLong(parts[2]),
                Integer.parseInt(parts[3])
            )
            if (info2.flags and MediaCodec.BUFFER_FLAG_CODEC_CONFIG != 0) {
                //videoResolution.x = Integer.parseInt(parts[4]);
                //videoResolution.y = Integer.parseInt(parts[5]);
            }
        } catch (e: NumberFormatException) {
            e.printStackTrace()
            Log.d(TAG, "===========Exception = " + e.message + " =================")
            //TODO: Need to stop the decoder or to skip the current decoder loop
            //showToast(e.getMessage());
        }

    }

}