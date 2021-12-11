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
/**
 *
 */
data class SignUpUseCaseOutput(private val result: Result<Unit>)

/**
 *
 */
fun interface SignUpUseCaseOutputPort {
    /**
     *
     */
    fun handle(output: SignUpUseCaseOutput)
}