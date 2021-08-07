package com.uramnoil.serverist.application.user.commands

import com.benasher44.uuid.Uuid

actual interface UpdateUserProfileCommandInputPort {
    fun execute(id: Uuid, accountId: String, name: String, description: String)
}

interface UpdateUserProfileCommandOutputPort {
    fun handle(result: Result<Unit>)
}
