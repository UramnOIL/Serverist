package com.uramnoil.serverist.auth.infrastructure.application

import java.util.UUID

data class AuthenticatedUser(val id: Uuid, val email: String, val hashedPassword: String)
