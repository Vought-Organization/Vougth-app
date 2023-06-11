package com.example.vought.rest

import com.example.vought.model.Event
import com.example.vought.model.EventRegister
import com.example.vought.model.UserData
import com.example.vought.model.UserDataUpdate

import retrofit2.Call

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface RetrofitService {

    @GET("v1/users")
    fun getAllUsers() : Call<List<UserData>>


    @POST("v1/users")
    fun saveUser(@Body user: UserData) : Call<UserData>

    @PUT("v1/users/{id}")
    fun updateUserById(@Path("id") id: String, @Body userData: UserDataUpdate): Call<UserData>


    @GET("v1/users/{idUser}")
    fun getUserById(@Path("idUser") id: String): Call<UserDataUpdate>

    @POST("v1/events")
    fun saveEvent(@Body event: EventRegister) : Call<EventRegister>

    @GET("v1/events")
    fun getEvent() : Call<List<Event>>

    @GET("v1/events/find-category?category=shows")
    fun getEventShows() : Call<List<Event>>

    @GET("v1/events/find-category?category=palestras")
    fun getEventPalestras() : Call<List<Event>>

    @GET("v1/events/find-category?category=teatros")
    fun getEventTeatros() : Call<List<Event>>

    @GET("v1/events/find-category?category=passeios")
    fun getEventPasseios() : Call<List<Event>>

    @GET("v1/events/find-category?category=congressos")
    fun getEventCongressos() : Call<List<Event>>

    @GET("v1/events/find-category?category=infantil")
    fun getEventInfantil() : Call<List<Event>>

    @GET("v1/events/find-category?category=standup")
    fun getEventStandup() : Call<List<Event>>

    @GET("{cep}/json")
    fun getAddress(@Path("cep") cep: String): Call<EventRegister>

}
