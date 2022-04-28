package com.uramnoil.serverist.auth.domain.kernel.model

import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec

class PasswordTest : FunSpec({
    context("正常系") {
        test("8文字") {
            shouldNotThrow<IllegalArgumentException> {
                com.uramnoil.serverist.auth.domain.models.Password("hoge1234")
            }
        }
        test("記号つき") {
            shouldNotThrow<IllegalArgumentException> {
                com.uramnoil.serverist.auth.domain.models.Password("a1!\"#\$%&'()*+,-./:;<=>?[\\]^_`{|}~")
            }
        }
        test("大文字") {
            shouldNotThrow<IllegalArgumentException> {
                com.uramnoil.serverist.auth.domain.models.Password("ABCD1234")
            }
        }
    }

    context("異常系") {
        test("7文字") {
            shouldThrow<IllegalArgumentException> {
                com.uramnoil.serverist.auth.domain.models.Password("hoge123")
            }
        }

        test("英字のみ") {
            shouldThrow<IllegalArgumentException> {
                com.uramnoil.serverist.auth.domain.models.Password("hogefuga")
            }
        }

        test("数字のみ") {
            shouldThrow<IllegalArgumentException> {
                com.uramnoil.serverist.auth.domain.models.Password("12345678")
            }
        }

        test("記号のみ") {
            shouldThrow<IllegalArgumentException> {
                com.uramnoil.serverist.auth.domain.models.Password("!\"#\$%&'()*+,-./:;<=>?[\\]^_`{|}~")
            }
        }
    }
})
