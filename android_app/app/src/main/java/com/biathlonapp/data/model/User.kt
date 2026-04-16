package com.biathlonapp.data.model

data class User(
    val id: Int,
    val email: String,
    val token: String? = null
)

