package com.uramnoil.serverist.auth.application.authenticated.queries

import com.benasher44.uuid.Uuid

data class User(val id: Uuid, val email: String, val hashedPassword: String)