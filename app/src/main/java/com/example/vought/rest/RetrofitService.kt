package com.example.vought.rest

import com.example.vought.model.LoginRequest
import com.example.vought.model.LoginResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface RetrofitService {

    @POST("login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    companion object {
        private val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val instance: RetrofitService by lazy {
            retrofit.create(RetrofitService::class.java)
        }
    }
}
