package com.uramnoil.serverist.application.user

import java.util.*
import com.uramnoil.serverist.domain.models.user.User as DomainUser

data class User(
    val id: UUID,
    val accountId: String,
    val name: String,
    val description: String
)

fun DomainUser.toApplication() = User(
    id = id.value,
    accountId = accountId.value,
    name = name.value,
    description = description.value
)