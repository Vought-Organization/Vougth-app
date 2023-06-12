package com.example.vought.model

import java.math.BigDecimal

data class Ticket(
    val idTicket: Int,
    val priceTicket: Double,
    val codeTicket: String,
    val eventIdEvent: Int
)
