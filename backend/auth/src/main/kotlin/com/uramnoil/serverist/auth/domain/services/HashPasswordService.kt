package com.uramnoil.serverist.auth.domain.services

import com.uramnoil.serverist.auth.domain.models.user.HashedPassword
import com.uramnoil.serverist.auth.domain.models.user.Password

/**
 * パスワードのハッシュと評価
 */
interface HashPasswordService {
    /**
     * 生パスワードのハッシュ
     * @param password  生パスワード
     * @return ハッシュ済みパスワード
     */
    fun hash(password: Password): HashedPassword

    /**
     * パスワードとハッシュ済みパスワードの評価
     */
    fun check(
        password: Password,
        hashedPassword: HashedPassword
    ): Boolean
}
