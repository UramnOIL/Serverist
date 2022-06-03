package com.uramnoil.serverist.auth.domain.models.user

/**
 * ハッシュ済みパスワード
 * Passwordからはservices.HashPasswordServiceを使う
 */
data class HashedPassword(val value: String)
