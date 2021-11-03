package com.uramnoil.serverist.auth.application.unauthenticated

import com.benasher44.uuid.Uuid


interface TryAuthEmailUseCaseInputPort {
    fun execute(token: Uuid)
}

interface TryAuthEmailUseCaseOutputPort {
    fun handle(result: Result<Unit>)
}