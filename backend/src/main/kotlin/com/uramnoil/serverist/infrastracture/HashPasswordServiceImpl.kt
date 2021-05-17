package com.uramnoil.serverist.infrastracture

import com.uramnoil.serverist.domain.services.user.HashPasswordService
import org.mindrot.jbcrypt.BCrypt

class HashPasswordServiceImpl : HashPasswordService {
    override fun hash(password: String): String = BCrypt.hashpw(password, BCrypt.gensalt())

    override fun check(password: String, hash: String): Boolean = BCrypt.checkpw(password, hash)
}