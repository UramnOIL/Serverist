package com.uramnoil.serverist.application.auth

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
    fun execute(email: String, password: String)
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