package com.uramnoil.serverist.application.server.commands

import com.benasher44.uuid.Uuid

interface DeleteServerCommandUseCaseInputPort {
    fun execute(id: Uuid)
}

interface DeleteServerCommandUseCaseOutputPort {
    fun handle(result: Result<Unit>)
}