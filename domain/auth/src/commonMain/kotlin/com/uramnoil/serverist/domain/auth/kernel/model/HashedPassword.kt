package com.uramnoil.serverist.domain.auth.kernel.model

/**
 * ハッシュ済みパスワード
 * Passwordからはservices.HashPasswordServiceを使う
 */
data class HashedPassword(val value: String)
