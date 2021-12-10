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
fun interface UpdateUserCommandUseCaseOutputPort {
    /**
     *
     */
    fun handle(result: Result<Unit>)
}