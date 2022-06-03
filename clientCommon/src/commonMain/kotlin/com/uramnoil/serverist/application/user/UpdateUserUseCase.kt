package com.uramnoil.serverist.application.user

data class UpdateUserUseCaseInput(val accountId: String, val name: String, val description: String)

/**
 *
 */
fun interface UpdateUserUseCaseInputPort {
    /**
     *
     */
    fun execute(input: UpdateUserUseCaseInput)
}

/**
 *
 */
data class UpdateUserUseCaseOutput(val result: Result<Unit>)

/**
 *
 */
fun interface UpdateUserUseCaseOutputPort {
    /**
     *
     */
    fun handle(output: UpdateUserUseCaseOutput)
}
