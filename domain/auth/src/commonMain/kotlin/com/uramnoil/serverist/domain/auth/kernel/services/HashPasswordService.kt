package com.uramnoil.serverist.domain.auth.kernel.services

import com.uramnoil.serverist.domain.auth.kernel.model.HashedPassword
import com.uramnoil.serverist.domain.auth.kernel.model.Password

/**
 * パスワードのハッシュと評価
 */
interface HashPasswordService {
    /**
     * 生パスワードのハッシュ
     * @param password  生パスワード
     * @return          ハッシュ済みパスワード
     */
    fun hash(password: Password): HashedPassword

    /**
     * パスワードとハッシュ済みパスワードの評価
     */
    fun check(
        password: Password, hashedPassword: HashedPassword
    ): Boolean
}