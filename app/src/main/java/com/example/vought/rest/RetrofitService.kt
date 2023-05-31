package com.example.vought.rest

import com.example.vought.model.Event
import com.example.vought.model.UserData
import com.example.vought.model.UserDataUpdate
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface RetrofitService {

    @GET("v1/users")
    fun getAllUsers() : Call<List<UserData>>

    @POST("v1/users")
    fun saveUser(@Body user: UserData) : Call<UserData>

    @PUT
    fun updateProfile(@Body userDataUpdate: UserDataUpdate) : Call<UserDataUpdate>

    @POST("v1/events")
    fun saveEvent(@Body event: Event) : Call<Event>

    @GET("v1/events")
    fun getEvent() : Call<List<Event>>
}