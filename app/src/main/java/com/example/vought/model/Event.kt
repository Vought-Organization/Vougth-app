package com.example.vought.model

import java.time.LocalDateTime

data class Event(
    val idEvent: Int,
    val cep: String,
    val nameEvent: String,
    val category: String,
    val description: String,
    val addressEvent: String,
    val city: String,
    val state: String,
    val startData: String,
    val endData: String,
    val latitude: String,
    val longitude: String,
    val photoProfile: String,
)
