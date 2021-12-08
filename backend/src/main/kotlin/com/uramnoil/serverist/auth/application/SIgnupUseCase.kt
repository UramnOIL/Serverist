package com.uramnoil.serverist.auth.application

import java.util.*

/**
 * サインアップ用ユースケース
 * クライアントサイドで使用
 */

/**
 *
 */
fun interface SignupUseCaseInputPort {
    /**
     *
     */
    fun execute(email: String, password: String, activationCode: UUID)
}

/**
 *
 */
fun interface SignupUseCaseOutputPort {
    /**
     *
     */
    fun handle(result: Result<Unit>)
}