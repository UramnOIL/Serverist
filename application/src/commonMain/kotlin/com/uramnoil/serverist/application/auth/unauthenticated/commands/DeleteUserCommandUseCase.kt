package com.uramnoil.serverist.application.auth.unauthenticated.commands

import com.benasher44.uuid.Uuid

interface DeleteUserCommandInputPort {
    fun execute(id: Uuid)
}

fun interface DeleteUserCommandOutputPort {
    fun handle(result: Result<Unit>)
}