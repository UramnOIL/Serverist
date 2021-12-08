package com.uramnoil.serverist.auth.application

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
    fun execute(email: String, password: String, activateCode: String)
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