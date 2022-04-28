package com.uramnoil.serverist.auth.domain.models

/**
 * ハッシュ済みパスワード
 * Passwordからはservices.HashPasswordServiceを使う
 */
data class HashedPassword(val value: String)
