package de.jensklingenberg.sheasy.utils.extension

import java.nio.ByteBuffer
import java.nio.charset.Charset


fun str_to_bb(msg: String, charset: Charset): ByteBuffer {
    return ByteBuffer.wrap(msg.toByteArray(charset))
}

fun bb_to_str(buffer: ByteBuffer, charset: Charset): String {
    val bytes: ByteArray
    if (buffer.hasArray()) {
        bytes = buffer.array()
    } else {
        bytes = ByteArray(buffer.remaining())
        buffer.get(bytes)
    }
    return String(bytes, charset)
}