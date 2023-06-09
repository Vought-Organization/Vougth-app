package com.example.vought.model

data class UserDataUpdate(
    val idUser: String,
    val userName: String,
    val email: String,
    val cpf: String,
    val password: String,
    val cep: String
)