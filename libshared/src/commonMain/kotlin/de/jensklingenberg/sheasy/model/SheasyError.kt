package de.jensklingenberg.sheasy.model

sealed class SheasyError(override var message: String):Throwable() {
    class NetworkError : SheasyError("No Connection")
    class NotAuthorizedError : SheasyError("NotAuthorizedError")
    class NoSharedFoldersError : SheasyError("No shared folders")
    class UploadFailedError : SheasyError("Upload failed")
    class PermissionDenied : SheasyError("Permission denied")
 class PathNotFoundError: SheasyError("Path not found")

    class UNKNOWNERROR : SheasyError("Unknown SheasyError")
}