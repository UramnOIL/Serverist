package com.uramnoil.serverist.application.user.commands

import com.benasher44.uuid.Uuid

interface UpdateUserProfileCommandUseCaseInputPort {
    fun execute(id: Uuid, accountId: String, name: String, description: String)
}

interface UpdateUserProfileCommandUseCaseOutputPort {
    fun handle(result: Result<Unit>)
}
