package com.uramnoil.serverist.serverist.domain.models.user

import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec

class AccountIdTest : FunSpec({
    context("正常系") {
        test("3文字") {
            shouldNotThrow<IllegalArgumentException> {
                AccountId((1..3).map { 'v' }.joinToString(""))
            }
        }

        test("15文字") {
            shouldNotThrow<IllegalArgumentException> {
                AccountId((1..15).map { 'v' }.joinToString(""))
            }
        }

        test("_を含む") {
            shouldNotThrow<IllegalArgumentException> {
                AccountId("_abc123")
            }
        }
    }

    context("異常系") {
        test("2文字") {
            shouldThrow<IllegalArgumentException> {
                AccountId((1..2).map { 'v' }.joinToString(""))
            }
        }

        test("16文字") {
            shouldThrow<IllegalArgumentException> {
                AccountId((1..16).map { 'v' }.joinToString(""))
            }
        }

        test("_しか含まない") {
            shouldThrow<IllegalArgumentException> {
                AccountId("____")
            }
        }

        test("数字しか含まない") {
            shouldThrow<IllegalArgumentException> {
                AccountId("1234")
            }
        }

        test("_または数字しか含まない") {
            shouldThrow<IllegalArgumentException> {
                AccountId("____1234")
            }
        }

        test("\"半角英数字 + _\"以外") {
            shouldThrow<IllegalArgumentException> {
                AccountId("abcあいうえお")
            }
            shouldThrow<IllegalArgumentException> {
                AccountId("abc[")
            }
            shouldThrow<IllegalArgumentException> {
                AccountId("abc\uD83D\uDE01")
            }
        }
    }
})
