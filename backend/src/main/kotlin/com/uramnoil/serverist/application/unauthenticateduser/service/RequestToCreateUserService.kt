package com.uramnoil.serverist.application.unauthenticateduser.service

import com.uramnoil.serverist.application.unauthenticateduser.User

data class RequestToCreateUserServiceDto(
    val accountId: String,
    val email: String,
    val password: String,
    val name: String
)

interface RequestToCreateUserService {
    suspend fun execute(dto: RequestToCreateUserServiceDto): User
}