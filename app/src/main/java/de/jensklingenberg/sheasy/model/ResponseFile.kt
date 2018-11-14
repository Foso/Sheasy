package de.jensklingenberg.sheasy.model

import java.io.InputStream

data class ResponseFile(val fileInputStream: InputStream?, val mimeType: String?)
