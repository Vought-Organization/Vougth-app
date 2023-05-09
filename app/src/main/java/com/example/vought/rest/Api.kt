package com.example.vought.rest

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Api {
    var BASE_URL = "https://54.163.233.38:8080/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL).build()

    fun <T> createService(serviceClass:Class<T>):T {
        return retrofit.create(serviceClass)
    }
}
