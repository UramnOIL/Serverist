package com.uramnoil.serverist.application.user.commands

import com.benasher44.uuid.Uuid

interface DeleteUserCommand {
    suspend fun execute(id: Uuid)
}
