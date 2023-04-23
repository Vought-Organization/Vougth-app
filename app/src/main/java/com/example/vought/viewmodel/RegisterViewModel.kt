package com.example.vought.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.vought.model.UserData
import com.example.vought.repositories.UserRepository
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RegisterViewModel constructor(private val repository: UserRepository) : ViewModel() {
    val status = MutableLiveData<Boolean>()

    fun saveUse(userData: UserData){
        val request = this.repository.saveUser(userData)
        request.enqueue(object : Callback<ResponseBody>{
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.code() == 200){
                    status.postValue(true)
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                status.postValue(false)
            }

        })
    }

}