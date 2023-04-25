package com.example.vought.rest

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Api {
    var BASE_URL = "https://10.18.7.77:443/"
    var retrofit: Retrofit? = null;

    fun getApiUsers() : Retrofit {

        if(retrofit != null){
            return retrofit!!
        }
        val retrofitService = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
         this.retrofit = retrofit
        return retrofitService
    }
}