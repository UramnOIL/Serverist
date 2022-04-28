package com.uramnoil.serverist.serverist.domain.models.user

import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec

class DescriptionTest : FunSpec({
    context("正常系") {
        test("0文字") {
            shouldNotThrow<IllegalArgumentException> {
                Description("")
            }
        }

        test("255文字") {
            shouldNotThrow<IllegalArgumentException> {
                val string = (1..255).map { 'v' }.joinToString("")
                Description(string)
            }
        }
    }

    context("異常系") {
        test("256文字") {
            shouldThrow<IllegalArgumentException> {
                val string = (1..256).map { 'v' }.joinToString("")
                Description(string)
            }
        }
    }
})
