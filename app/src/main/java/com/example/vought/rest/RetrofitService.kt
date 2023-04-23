package com.example.vought.rest

import com.example.vought.model.UserData
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface RetrofitService {

    @GET("v1/users/login")
    fun getAllUsers() : Call<List<UserData>>

    @POST("v1/users")
    fun saveUser(@Body recipe: UserData) : Call<ResponseBody>

    companion object{

        private val retrofitService : RetrofitService by lazy{
            val retrofitService = Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            retrofitService.create(RetrofitService::class.java)
        }

        fun Instance() : RetrofitService {
            return retrofitService

        }
    }
}