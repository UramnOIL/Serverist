package com.uramnoil.serverist.application.unapproveduser

data class User(
    val accountId: String,
    val email: String,
    val hashedPassword: String,
    val name: String,
    val description: String
)
