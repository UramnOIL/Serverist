package com.uramnoil.serverist.serverist.user.infrastructure.application

import com.uramnoil.serverist.domain.common.user.Id
import com.uramnoil.serverist.domain.serverist.models.user.AccountId
import com.uramnoil.serverist.domain.serverist.models.user.Description
import com.uramnoil.serverist.domain.serverist.models.user.Name
import com.uramnoil.serverist.serverist.infrastructure.Users
import org.jetbrains.exposed.sql.ResultRow
import com.uramnoil.serverist.domain.serverist.models.user.User as DomainUser
import com.uramnoil.serverist.serverist.application.user.User as ApplicationUser

fun ResultRow.toApplicationUser() = ApplicationUser(
    this[Users.id].value,
    this[Users.accountId],
    this[Users.name],
    this[Users.description]
)

fun ResultRow.toDomainUser() = com.uramnoil.serverist.domain.serverist.models.user.User.new(
    Id(this[Users.id].value),
    AccountId(this[Users.accountId]),
    Name(this[Users.name]),
    Description(this[Users.description])
)

fun DomainUser.toApplicationUser() = ApplicationUser(
    id.value,
    accountId.value,
    name.value,
    description.value
)