package de.jensklingenberg.sheasy.network.api

import retrofit2.http.GET

interface SheasyAPI {

    @GET("share.json")
fun that(): String
}