package com.uramnoil.serverist.application.user


/**
 *
 */
interface FindAllUsersQueryUseCaseInputPort {
    /**
     *
     */
    fun execute()
}

/**
 *
 */
fun interface FindAllUsersQueryUseCaseOutputPort {
    /**
     *
     */
    fun handle(result: Result<List<User>>)
}