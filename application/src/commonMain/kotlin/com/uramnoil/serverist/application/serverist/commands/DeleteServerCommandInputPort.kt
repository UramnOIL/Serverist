package com.uramnoil.serverist.application.serverist.commands

import com.benasher44.uuid.Uuid

interface DeleteServerCommandInputPort {
    fun execute(id: Uuid)
}

interface DeleteServerCommandOutputPort {
    fun handle(result: Result<Unit>)
}