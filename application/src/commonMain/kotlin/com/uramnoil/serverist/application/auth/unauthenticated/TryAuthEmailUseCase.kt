package com.uramnoil.serverist.application.auth.unauthenticated

import com.benasher44.uuid.Uuid


interface TryAuthEmailUseCaseInputPort {
    fun execute(token: Uuid)
}

interface TryAuthEmailUseCaseOutputPort {
    fun handle(result: Result<Unit>)
}