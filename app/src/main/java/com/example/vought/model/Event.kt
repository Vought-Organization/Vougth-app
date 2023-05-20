package com.example.vought.model

import java.time.LocalDateTime

data class Event(
    val cep: String,
    val nameEvent: String,
    val categoryEvent: String,
    val description: String,
    val addressEvent: String,
    val city: String,
    val state: String,
    val startData: String,
    val endData: String
)
