package de.jensklingenberg.sheasy.extension

import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException

/**
 * Created by jens on 31/3/18.
 */
@Throws(IOException::class)
fun File.copyTo( dst: File) {
    val inStream = FileInputStream(this)
    val outStream = FileOutputStream(dst)
    val inChannel = inStream.channel
    val outChannel = outStream.channel
    inChannel.transferTo(0, inChannel.size(), outChannel)
    inStream.close()
    outStream.close()
}