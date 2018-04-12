package de.jensklingenberg.sheasy.model

/**
 * Created by jens on 17/2/18.
 */
data class DownloadRequest(val url: String, val dest: String)

data class IntentRequest(val action: String)