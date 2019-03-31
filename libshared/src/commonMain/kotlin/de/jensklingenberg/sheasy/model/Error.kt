package de.jensklingenberg.sheasy.model

sealed class Error(var message: String) {
    class NetworkError : Error("No Connection")
    class NotAuthorizedError : Error("NotAuthorizedError")
    class NoSharedFoldersError : Error("No shared folders")
    class UNKNOWNERROR : Error("Unknown Error")
}