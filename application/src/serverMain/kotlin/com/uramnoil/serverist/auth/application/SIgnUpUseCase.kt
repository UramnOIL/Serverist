package com.uramnoil.serverist.auth.application

import java.util.UUID

/**
 * サインアップ用ユースケース
 * クライアントサイドで使用
 */

/**
 *
 */
fun interface SignUpUseCaseInputPort {
    /**
     *
     */
    fun execute(email: String, password: String, activationCode: UUID)
}

/**
 *
 */
fun interface SignUpUseCaseOutputPort {
    /**
     *
     */
    fun handle(result: Result<Unit>)
}
