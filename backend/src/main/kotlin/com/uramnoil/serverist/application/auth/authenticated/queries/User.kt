package com.uramnoil.serverist.application.auth.authenticated.queries

import com.benasher44.uuid.Uuid

data class User(val id: Uuid, val email: String, val hashedPassword: String)