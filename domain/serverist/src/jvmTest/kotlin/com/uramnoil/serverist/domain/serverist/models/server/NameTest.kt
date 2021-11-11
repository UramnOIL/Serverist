package com.uramnoil.serverist.domain.serverist.models.server

import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec

class NameTest : FunSpec({
    context("正常系") {
        test("1文字") {
            shouldNotThrow<IllegalArgumentException> {
                val string = (1..1).map { 'v' }.joinToString("")
                Name(string)
            }
        }

        test("31文字") {
            shouldNotThrow<IllegalArgumentException> {
                val string = (1..31).map { 'v' }.joinToString("")
                Name(string)
            }
        }
    }

    context("異常系") {
        test("空文字") {
            shouldThrow<IllegalArgumentException> {
                Name("")
            }
        }

        test("空白文字") {
            shouldThrow<IllegalArgumentException> {
                Name(" ")    //半角
            }

            shouldThrow<IllegalArgumentException> {
                Name("　")    //全角
            }

            shouldThrow<IllegalArgumentException> {
                Name("   ")    //TAB
            }
        }

        test("前後空白") {
            shouldThrow<IllegalArgumentException> {
                Name(" hoge")
            }

            shouldThrow<IllegalArgumentException> {
                Name("hoge ")
            }


            shouldThrow<IllegalArgumentException> {
                Name(" hoge ")
            }
        }

        test("32文字") {
            val string = (1..32).map { 'v' }.joinToString("")
            shouldThrow<IllegalArgumentException> {
                Name(string)
            }
        }
    }
})
