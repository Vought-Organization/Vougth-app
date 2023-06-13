package com.example.vought.model

import java.time.LocalDateTime

data class EventRegister(
    val cep: String,
    val nameEvent: String,
    val description: String,
    val category: String,
    val latitude: String,
    val longitude: String,
    val addressEvent: String,
    val city: String,
    val state: String,
    val startData: String,
    val endData: String
)
