package de.jensklingenberg.sheasy.model

 data class Device(val ip: String,val name:String="",val authorizationType: AuthorizationType=AuthorizationType.UNKNOWN)
