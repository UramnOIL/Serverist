package com.uramnoil.serverist.application.user.commands

import com.benasher44.uuid.Uuid

interface DeleteUserCommandInputPort {
    fun execute(id: Uuid)
}

interface DeleteUserCommandOutputPort {
    fun handle(result: Result<Unit>)
}
