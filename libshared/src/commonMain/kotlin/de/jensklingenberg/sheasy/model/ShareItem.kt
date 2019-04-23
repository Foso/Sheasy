package de.jensklingenberg.sheasy.model

data class ShareItem(
    val message: String?
)

enum class ShareType{
    INCOMING,OUTGOING
}