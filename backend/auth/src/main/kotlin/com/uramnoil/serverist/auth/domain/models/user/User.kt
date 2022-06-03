package com.uramnoil.serverist.auth.domain.models.user

import com.uramnoil.serverist.common.domain.models.kernel.UserId

/**
 * 認証済みユーザエンティティ
 *
 * @param userId                serveristコンテキストのユーザIDと同じValue Objectを使う
 * @param email
 * @param hashedPassword
 */
class User private constructor(
    val userId: UserId,
    var email: Email,
    var hashedPassword: HashedPassword,
) {
    companion object {
        /**
         * runCatchingでコンストラクタをラップした関数
         */
        fun new(
            userId: UserId,
            email: Email,
            hashedPassword: HashedPassword,
        ) = kotlin.runCatching {
            User(
                userId,
                email,
                hashedPassword,
            )
        }
    }
}
