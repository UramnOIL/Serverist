package com.uramnoil.serverist.infrastracture.user

import com.uramnoil.serverist.domain.common.user.Id
import com.uramnoil.serverist.domain.user.models.AccountId
import com.uramnoil.serverist.domain.user.models.Description
import com.uramnoil.serverist.domain.user.models.Name
import com.uramnoil.serverist.infrastructure.user.Users
import org.jetbrains.exposed.sql.ResultRow
import com.uramnoil.serverist.application.user.User as ApplicationUser
import com.uramnoil.serverist.domain.user.models.User as DomainUser

fun ResultRow.toApplicationUser() = ApplicationUser(
    this[Users.id].value,
    this[Users.accountId],
    this[Users.name],
    this[Users.description]
)

fun ResultRow.toDomainUser() = DomainUser.new(
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