package de.jensklingenberg.model.response

data class Response<T>(val status: String, val data: T?, val message: String?)