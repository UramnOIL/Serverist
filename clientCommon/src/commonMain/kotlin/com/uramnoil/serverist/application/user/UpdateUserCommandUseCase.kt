package com.uramnoil.serverist.application.user


data class UpdateUserCommandUseCaseInput(val accountId: String, val name: String, val description: String)

/**
 *
 */
interface UpdateUserCommandUseCaseInputPort {
    /**
     *
     */
    fun execute(input: UpdateUserCommandUseCaseInput)
}

/**
 *
 */
data class UpdateUserCommandUseCaseOutput(private val result: Result<Unit>)

/**
 *
 */
fun interface UpdateUserCommandUseCaseOutputPort {
    /**
     *
     */
    fun handle(output: UpdateUserCommandUseCaseOutput)
}