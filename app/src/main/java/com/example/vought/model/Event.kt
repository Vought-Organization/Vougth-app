package com.example.vought.model

import java.time.LocalDateTime

data class Event(
    val cep: String,
    val name_event: String,
    val category_event: String,
    val description: String,
    val addressEvent: String,
    val city: String,
    val state: String,
    val latitude: String,
    val longitude: String,
    val photoProfile: String
)

fun teste() {
    val latitudeTeste = "-21.234567"
    val latitudeNumero = latitudeTeste.toDouble()
}
