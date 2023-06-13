package com.example.vought.model

data class EventDataUpdate(
    val idEvent: Int,
    val cep: String,
    val nameEvent: String,
    val category: String,
    val description: String,
    val latitude: String,
    val longitude: String,
    val addressEvent: String,
    val city: String,
    val state: String,
    val startData: String,
    val endData: String,

)