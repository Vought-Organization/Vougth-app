package com.example.vought.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vought.model.LoginResponse
import com.example.vought.repositories.UserRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class LoginViewModel(private val repository: UserRepository) : ViewModel() {

    private val _loginResult = MutableLiveData<Result<LoginResponse>>()
    val loginResult: LiveData<Result<LoginResponse>> = _loginResult

    fun doLogin(email: String, password: String) {
        viewModelScope.launch {
            try {
                val response = repository.login(email, password)
                _loginResult.postValue(response)
            } catch (e: IOException) {
                _loginResult.postValue(Result.failure(e))
            } catch (e: HttpException) {
                _loginResult.postValue(Result.failure(e))
            }
        }
    }
}
