package com.example.vought.model

data class UserData(
    val name: String,
    val email: String,
    val cpf: String,
    val password: String,
)
data class LoginResponse(
    val success: Boolean,
    val message: String?,
    val user: UserData?
)
data class LoginRequest(
    val email: String,
    val password: String
)