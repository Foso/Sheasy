package de.jensklingenberg.sheasy.model

data class Response<T>(val status: String, val data: T?, val message: String?)