package com.uramnoil.serverist.application.unauthenticateduser.commands

import com.benasher44.uuid.Uuid

actual interface DeleteUnauthenticatedUserCommandInputPort {
    fun execute(id: Uuid)
}

interface DeleteUnauthenticatedUserCommandOutputPort {
    fun handle(result: Result<Unit>)
}