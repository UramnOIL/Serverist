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
/**
 *
 */
data class FindAllUsersQueryUseCaseOutput(private val result: Result<List<User>>)

/**
 *
 */
fun interface FindAllUsersQueryUseCaseOutputPort {
    /**
     *
     */
    fun handle(output: FindAllUsersQueryUseCaseOutput)
}