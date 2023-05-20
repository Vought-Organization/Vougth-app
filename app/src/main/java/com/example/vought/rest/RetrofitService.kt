package com.example.vought.rest

import com.example.vought.model.Event
import com.example.vought.model.UserData
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface RetrofitService {

    @GET("v1/users")
    fun getAllUsers() : Call<List<UserData>>

    @POST("v1/users")
    fun saveUser(@Body user: UserData) : Call<UserData>

    @POST("v1/events")
    fun saveEvent(@Body event: Event) : Call<Event>

    @GET("events")
    fun getEvent() : Call<List<Event>>
}