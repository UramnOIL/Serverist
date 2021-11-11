package com.uramnoil.serverist.auth.application.authenticated.queries

import java.util.*

data class User(val id: UUID, val email: String, val hashedPassword: String)