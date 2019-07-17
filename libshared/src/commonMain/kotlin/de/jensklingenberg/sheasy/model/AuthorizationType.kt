package de.jensklingenberg.sheasy.model

enum class AuthorizationType {

    /**
     * The authorization of a connected device was removed or not yet set
     */
    UNAUTH,

    /**
     * The device is authorized to connect to the server
     */
    AUTHORIZED,

    UNKNOWN
}