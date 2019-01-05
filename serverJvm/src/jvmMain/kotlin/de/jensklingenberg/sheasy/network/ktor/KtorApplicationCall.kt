package de.jensklingenberg.sheasy.network.ktor

data class KtorApplicationCall(val parameters: Map<String,String> = emptyMap(), val remoteHostIp : String="",var parameter:String="",val requestedApiPath:String="")