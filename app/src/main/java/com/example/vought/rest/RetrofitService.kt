package com.example.vought.rest

import com.example.vought.model.Address
import com.example.vought.model.Event
import com.example.vought.model.EventRegister
import com.example.vought.model.Ticket
import com.example.vought.model.UserData
import com.example.vought.model.UserDataUpdate

import retrofit2.Call
import retrofit2.http.*

interface RetrofitService {

    @GET("v1/users")
    fun getAllUsers() : Call<List<UserData>>

    @POST("v1/users")
    fun saveUser(@Body user: UserData) : Call<UserData>

    @PUT("v1/users/{id}")
    fun updateUserById(@Path("id") id: String, @Body userData: UserDataUpdate): Call<UserData>

    @DELETE("v1/events/{id}")
    fun deleteEvent(@Path("id") id: Int): Call<Void>

    @GET("v1/users/{idUser}")
    fun getUserById(@Path("idUser") id: String): Call<UserDataUpdate>

    @GET("v1/files/qtty/3")
    fun getUltimosEventos() : Call<List<Event>>

    @POST("v1/events")
    fun saveEvent(@Body event: EventRegister) : Call<EventRegister>

    @GET("v1/events")
    fun getEvent() : Call<List<Event>>

    @GET("v1/events/find-category?category=show")
    fun getEventShows() : Call<List<Event>>

    @GET("v1/events/find-category?category=palestra")
    fun getEventPalestras() : Call<List<Event>>

    @GET("v1/events/find-category?category=teatro")
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
    fun getAddress(@Path("cep") cep: String): Call<Address>


    @GET("events/{id}")
    fun getTickets(@Path("id") id: Int): Call<List<Ticket>>
}
