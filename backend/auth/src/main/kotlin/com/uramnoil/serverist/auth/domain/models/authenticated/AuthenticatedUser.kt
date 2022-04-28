package com.uramnoil.serverist.auth.domain.models.authenticated

import com.uramnoil.serverist.common.domain.models.kernel.UserId

/**
 * 認証済みユーザエンティティ
 *
 * @param userId                serveristコンテキストのユーザIDと同じValue Objectを使う
 * @param email
 * @param hashedPassword
 */
class AuthenticatedUser private constructor(
    val userId: UserId,
    var email: com.uramnoil.serverist.auth.domain.models.Email,
    var hashedPassword: com.uramnoil.serverist.auth.domain.models.HashedPassword,
) {
    companion object {
        /**
         * runCatchingでコンストラクタをラップした関数
         */
        fun new(
            userId: UserId,
            email: com.uramnoil.serverist.auth.domain.models.Email,
            hashedPassword: com.uramnoil.serverist.auth.domain.models.HashedPassword,
        ) = kotlin.runCatching {
            AuthenticatedUser(
                userId,
                email,
                hashedPassword,
            )
        }
    }
}
