package com.uramnoil.serverist.application.auth.unauthenticated.commands

import com.benasher44.uuid.Uuid

interface DeleteUnauthenticatedUserCommandInputPort {
    fun execute(id: Uuid)
}

fun interface DeleteUnauthenticatedUserCommandOutputPort {
    fun handle(result: Result<Unit>)
}