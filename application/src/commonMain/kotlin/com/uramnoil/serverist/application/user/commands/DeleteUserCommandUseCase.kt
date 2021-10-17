package com.uramnoil.serverist.application.user.commands

import com.benasher44.uuid.Uuid

interface DeleteUserCommandUseCaseInputPort {
    fun execute(id: Uuid)
}

interface DeleteUserCommandUseCaseOutputPort {
    fun handle(result: Result<Unit>)
}
