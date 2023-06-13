package com.example.vought.model

data class TicketRegisterData(
    val precoIngresso: Double,
    val event: Evento
) {
    data class Evento (
        val idEvent: Int
    )
}
