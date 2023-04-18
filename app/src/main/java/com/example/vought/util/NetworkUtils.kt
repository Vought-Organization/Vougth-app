package com.ocanha.retrofitcomkotlin.rest

import com.example.vought.model.UserData
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface RetrofitService {

    @GET("recipes")
    fun getAllRecipes(): Call<List<UserData>>

    @POST("recipes")
    fun saveRecipe(@Body recipe: UserData): Call<ResponseBody>

    companion object {

        private val retrofitService: RetrofitService by lazy {

            val retrofit = Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            retrofit.create(RetrofitService::class.java)

        }

        fun getInstance(): RetrofitService {
            return retrofitService
        }
    }

}