package com.uramnoil.serverist.auth.application

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
    fun execute(email: String, password: String, activationCode: String)
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
