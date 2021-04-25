package com.uramnoil.serverist.application.user

data class User(
    val id: String,
    val accountId: String,
    val email: String,
    val hashedPassword: String,
    val name: String,
    val description: String
)
