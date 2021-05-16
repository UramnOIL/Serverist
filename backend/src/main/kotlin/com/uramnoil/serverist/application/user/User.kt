package com.uramnoil.serverist.application.user

import com.uramnoil.serverist.application.kernel.Server
import java.util.*

data class User(
    val id: UUID,
    val accountId: String,
    val name: String,
    val description: String,
    val servers: List<Server>
)