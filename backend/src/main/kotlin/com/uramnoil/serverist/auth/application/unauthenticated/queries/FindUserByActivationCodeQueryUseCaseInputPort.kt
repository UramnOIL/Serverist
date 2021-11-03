package com.uramnoil.serverist.auth.application.unauthenticated.queries

import java.util.*

interface FindUserByActivationCodeQueryUseCaseInputPort {
    /**
     * @return 未認証ユーザーのID
     */
    suspend fun execute(activationCode: UUID): Result<User?>
}