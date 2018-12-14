package de.jensklingenberg.sheasy.ui.settings

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.PixelFormat
import android.graphics.Point
import android.hardware.display.DisplayManager
import android.hardware.display.VirtualDisplay
import android.media.Image
import android.media.ImageReader
import android.media.projection.MediaProjection
import android.media.projection.MediaProjectionManager
import android.os.Handler
import android.os.Looper
import android.util.Base64
import android.util.Log
import android.view.Display
import android.view.OrientationEventListener
import android.view.WindowManager
import de.jensklingenberg.sheasy.App
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import javax.inject.Inject

class ScreenRecord : ImageReader.OnImageAvailableListener {

    interface ImageReadyListener {
        fun onImageReady(string: String)
    }


    companion object {

        val TAG = ScreenCaptureImage::class.java.name
        private val REQUEST_CODE = 100
        var STORE_DIRECTORY: String? = null
        var IMAGES_PRODUCED: Int = 0
        val SCREENCAP_NAME = "screencap"
        val VIRTUAL_DISPLAY_FLAGS =
            DisplayManager.VIRTUAL_DISPLAY_FLAG_OWN_CONTENT_ONLY or DisplayManager.VIRTUAL_DISPLAY_FLAG_PUBLIC
        private var sMediaProjection: MediaProjection? = null
    }

    @Inject
    lateinit var mProjectionManager: MediaProjectionManager

    @Inject
    lateinit var windowManager: WindowManager

    @Inject
    lateinit var context: Context

    private var mImageReader: ImageReader? = null
    private var mHandler: Handler? = null
    private var mDisplay: Display? = null
    private var mVirtualDisplay: VirtualDisplay? = null
    private var mDensity: Int = 0
    var mWidth: Int = 0
    var mHeight: Int = 0
    private var mRotation: Int = 0
    private var mOrientationChangeCallback: OrientationChangeCallback? = null
    var imageReadyListener: ImageReadyListener? = null

    init {
        initializeDagger()

    }

    private fun initializeDagger() = App.appComponent.inject(this)


    override fun onImageAvailable(reader: ImageReader?) {
        var image: Image? = null
        var fos: FileOutputStream? = null
        var bitmap: Bitmap? = null

        try {
            image = reader?.acquireLatestImage()
            if (image != null) {
                val planes = image.planes
                val buffer = planes[0].buffer
                val pixelStride = planes[0].pixelStride
                val rowStride = planes[0].rowStride
                val rowPadding = rowStride - pixelStride * mWidth

                // create bitmap
                bitmap = Bitmap.createBitmap(
                    mWidth + rowPadding / pixelStride,
                    mHeight,
                    Bitmap.Config.ARGB_8888
                )
                bitmap?.copyPixelsFromBuffer(buffer)

                // write bitmap to a file
                fos =
                        FileOutputStream("${STORE_DIRECTORY}/myscreen_${IMAGES_PRODUCED}.png")
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
                val byteArrayOutputStream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
                val byteArray = byteArrayOutputStream.toByteArray()

                val encoded = Base64.encodeToString(byteArray, Base64.DEFAULT)
                imageReadyListener?.onImageReady(encoded)
                Log.d(
                    TAG,
                    "${STORE_DIRECTORY}/myscreen_${IMAGES_PRODUCED}.png"
                )
                IMAGES_PRODUCED++
                Log.e(
                    TAG,
                    "captured image: ${IMAGES_PRODUCED}"
                )
            }

        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            if (fos != null) {
                try {
                    fos.close()
                } catch (ioe: IOException) {
                    ioe.printStackTrace()
                }

            }

            bitmap?.recycle()

            image?.close()
        }

    }

    fun startRecord(resultCode: Int, data: Intent?) {
        object : Thread() {
            override fun run() {
                Looper.prepare()
                mHandler = Handler()
                Looper.loop()
            }
        }.start()
        sMediaProjection = mProjectionManager.getMediaProjection(resultCode, data)

        sMediaProjection?.let {
            val externalFilesDir = context.getExternalFilesDir(null)
            if (externalFilesDir != null) {
                STORE_DIRECTORY = externalFilesDir.absolutePath + "/screenshots/"
                val storeDirectory = File(STORE_DIRECTORY)
                if (!storeDirectory.exists()) {
                    val success = storeDirectory.mkdirs()
                    if (!success) {
                        Log.e(TAG, "failed to create file storage directory.")
                        return
                    }
                }
            } else {
                Log.e(
                    TAG,
                    "failed to create file storage directory, getExternalFilesDir is null."
                )
                return
            }

            // display metrics
            val metrics = context.resources.displayMetrics
            mDensity = metrics.densityDpi
            mDisplay = windowManager.defaultDisplay
            // create virtual display depending on device width / height
            createVirtualDisplay()

            // register orientation change callback
            mOrientationChangeCallback =
                    OrientationChangeCallback(context)
            mOrientationChangeCallback?.let { orientationChangeCallback ->
                if (orientationChangeCallback.canDetectOrientation()) {
                    orientationChangeCallback.enable()
                }
            }


            // register media projection stop callback
            sMediaProjection?.registerCallback(MediaProjectionStopCallback(), mHandler)
        }
    }

    private inner class MediaProjectionStopCallback : MediaProjection.Callback() {
        override fun onStop() {
            Log.e("ScreenCapture", "stopping projection.")
            mHandler?.post {
                mVirtualDisplay?.release()
                mImageReader?.setOnImageAvailableListener(null, null)
                mOrientationChangeCallback?.disable()
                sMediaProjection?.unregisterCallback(this@MediaProjectionStopCallback)
            }
        }
    }


    inner class OrientationChangeCallback internal constructor(context: Context) :
        OrientationEventListener(context) {

        override fun onOrientationChanged(orientation: Int) {
            val rotation = mDisplay?.rotation ?: 0
            if (rotation != mRotation) {
                mRotation = rotation
                try {
                    // clean up
                    mVirtualDisplay?.release()
                    mImageReader?.setOnImageAvailableListener(null, null)
                    createVirtualDisplay()
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
        }
    }

    fun stopProjection() {
        mHandler?.post {
            sMediaProjection?.stop()
        }
    }

    fun createScreenCaptureIntent() = mProjectionManager.createScreenCaptureIntent()


    /****************************************** Factoring Virtual Display creation  */
    private fun createVirtualDisplay() {
        // get width and height
        val size = Point()
        mDisplay?.getSize(size)
        mWidth = size.x
        mHeight = size.y

        // start capture reader
        mImageReader = ImageReader.newInstance(mWidth, mHeight, PixelFormat.RGBA_8888, 2)



        mVirtualDisplay = sMediaProjection?.createVirtualDisplay(
            SCREENCAP_NAME,
            mWidth,
            mHeight,
            mDensity,
            VIRTUAL_DISPLAY_FLAGS,
            mImageReader?.surface,
            null,
            mHandler
        )
        mImageReader?.setOnImageAvailableListener(

            this
            , mHandler
        )
    }


}