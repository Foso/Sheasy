package de.jensklingenberg.sheasy.model

sealed class Error(override var message: String):Throwable() {
    class NetworkError : Error("No Connection")
    class NotAuthorizedError : Error("NotAuthorizedError")
    class NoSharedFoldersError : Error("No shared folders")
    class UploadFailedError : Error("Upload failed")

    class UNKNOWNERROR : Error("Unknown Error")
}