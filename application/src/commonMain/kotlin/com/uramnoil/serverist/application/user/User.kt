package com.uramnoil.serverist.application.user

import com.benasher44.uuid.Uuid
import com.uramnoil.serverist.domain.user.models.User as DomainUser

data class User(
    val id: Uuid,
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