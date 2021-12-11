package com.uramnoil.serverist.application.auth

/**
 * サインアップ用ユースケース
 * クライアントサイドで使用
 */

/**
 *
 */
data class SignUpUseCaseInput(val email: String, val password: String)

/**
 *
 */
fun interface SignUpUseCaseInputPort {
    /**
     *
     */
    fun execute(input: SignUpUseCaseInput)
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