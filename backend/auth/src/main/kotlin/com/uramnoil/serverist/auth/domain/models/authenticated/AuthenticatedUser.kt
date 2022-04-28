package com.uramnoil.serverist.auth.domain.models.authenticated

import com.uramnoil.serverist.domain.common.user.Id

/**
 * 認証済みユーザエンティティ
 *
 * @param id                serveristコンテキストのユーザIDと同じValue Objectを使う
 * @param email
 * @param hashedPassword
 */
class AuthenticatedUser private constructor(
    val id: Id,
    var email: com.uramnoil.serverist.auth.domain.models.Email,
    var hashedPassword: com.uramnoil.serverist.auth.domain.models.HashedPassword,
) {
    companion object {
        /**
         * runCatchingでコンストラクタをラップした関数
         */
        fun new(
            id: Id,
            email: com.uramnoil.serverist.auth.domain.models.Email,
            hashedPassword: com.uramnoil.serverist.auth.domain.models.HashedPassword,
        ) = kotlin.runCatching {
            AuthenticatedUser(
                id,
                email,
                hashedPassword,
            )
        }
    }
}
