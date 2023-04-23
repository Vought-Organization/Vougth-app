package com.example.vought.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.vought.model.UserData
import com.example.vought.repositories.UserRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginViewModel constructor(private val repository: UserRepository) : ViewModel() {
    val usersLiveData = MutableLiveData<List<UserData>>()
    var errorMessage = MutableLiveData<String>()

    fun getAllUsers(){

        val request = this.repository.getAllUsers()
        request.enqueue(object : Callback<List<UserData>>{
            override fun onResponse(call: Call<List<UserData>>, response: Response<List<UserData>>) {
                if (response.code() == 200){
                    //Deu certo minha requisição
                }else{
                    //Deu errado
                    errorMessage.postValue("Erro ao mostrar usuário")
                }
            }

            override fun onFailure(call: Call<List<UserData>>, t: Throwable) {
                errorMessage.postValue(t.message)
            }
        })
    }
}
