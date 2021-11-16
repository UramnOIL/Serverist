package com.uramnoil.serverist.domain.auth.authenticated.models

import com.uramnoil.serverist.domain.auth.kernel.model.Email
import com.uramnoil.serverist.domain.auth.kernel.model.HashedPassword
import com.uramnoil.serverist.domain.common.user.Id

/**
 * 認証済みユーザエンティティ
 *
 * @param id                serveristコンテキストのユーザIDと同じValue Objectを使う
 * @param email
 * @param hashedPassword
 */
class User private constructor(
    val id: Id,
    var email: Email,
    var hashedPassword: HashedPassword,
) {
    companion object {
        /**
         * runCatchingでコンストラクタをラップした関数
         */
        fun new(
            id: Id,
            email: Email,
            hashedPassword: HashedPassword,
        ) = kotlin.runCatching {
            User(
                id,
                email,
                hashedPassword,
            )
        }
    }
}