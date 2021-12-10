package com.uramnoil.serverist.application.user


/**
 *
 */
interface UpdateUserCommandUseCaseInputPort {
    /**
     *
     */
    fun execute(accountId: String, name: String, description: String)
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