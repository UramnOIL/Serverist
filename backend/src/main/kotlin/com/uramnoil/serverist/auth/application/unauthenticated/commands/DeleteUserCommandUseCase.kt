package com.uramnoil.serverist.auth.application.unauthenticated.commands

import java.util.*


interface DeleteUserCommandInputPort {
    suspend fun execute(id: UUID): Result<Unit>
}
