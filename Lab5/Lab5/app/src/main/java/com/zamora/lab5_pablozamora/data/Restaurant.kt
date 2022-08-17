package com.zamora.lab5_pablozamora.data

import java.io.Serializable

data class Restaurant(
    val name: String,
    val address: String,
    val office_hours: String,
    val phone_number: String
    ): Serializable
