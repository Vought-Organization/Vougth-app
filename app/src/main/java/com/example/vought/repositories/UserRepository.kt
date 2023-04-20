package com.example.vought.repositories

import com.example.vought.model.LoginRequest
import com.example.vought.model.LoginResponse
import com.example.vought.rest.RetrofitService
import retrofit2.HttpException

class UserRepository(private val retrofitService: RetrofitService) {

    suspend fun login(email: String, password: String): Result<LoginResponse> {
        return try {
            val request = LoginRequest(email, password)
            val response = retrofitService.login(request)
            if (response.isSuccessful) {
                val loginResponse = response.body()
                if (loginResponse != null) {
                    Result.success(loginResponse)
                } else {
                    Result.failure(Throwable("Empty response body"))
                }
            } else {
                val error = response.errorBody()?.string()
                Result.failure(Throwable("Failed to login: $error"))
            }
        } catch (e: HttpException) {
            Result.failure(Throwable("Failed to login: ${e.message}"))
        } catch (e: Throwable) {
            Result.failure(e)
        }
    }
}
