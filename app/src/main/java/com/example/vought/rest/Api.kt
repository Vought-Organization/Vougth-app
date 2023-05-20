package com.example.vought.rest

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Api {
    var BASE_URL = "https://voughtback.duckdns.org/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL).build()

    fun <T> createService(serviceClass:Class<T>):T {
        return retrofit.create(serviceClass)
    }
}
