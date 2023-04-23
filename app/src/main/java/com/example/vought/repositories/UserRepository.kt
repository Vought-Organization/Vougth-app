package com.example.vought.repositories

import com.example.vought.model.UserData
import com.example.vought.rest.RetrofitService

class UserRepository constructor(private val retrofitService: RetrofitService){

    fun saveUser(userData : UserData)  = retrofitService.saveUser(userData)
    fun getAllUsers() = retrofitService.getAllUsers()

}