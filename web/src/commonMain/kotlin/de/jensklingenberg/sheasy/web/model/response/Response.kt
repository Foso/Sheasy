package de.jensklingenberg.sheasy.web.model.response

data class Response<T>(val status: String, val data: T?, val message: String?)