package com.uramnoil.serverist.domain.serverist.models.server

import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec

class AddressTest : FunSpec({
    context("正常系") {
        test("example.com") {
            shouldNotThrow<IllegalArgumentException> {
                Address("example.com")
            }
        }

        test("192.168.0.1") {
            shouldNotThrow<IllegalArgumentException> {
                Address("192.168.0.1")
            }
        }
    }

    context("異常系") {
        test("hoge") {
            shouldThrow<IllegalArgumentException> {
                Address("hoge")
            }
        }

        test("example.123") {
            shouldThrow<IllegalArgumentException> {
                Address("hoge.123")
            }
        }

        test("example.a") {
            shouldThrow<IllegalArgumentException> {
                Address("example.a")
            }
        }

        test("192.168.1") {
            shouldThrow<IllegalArgumentException> {
                Address("192.168.1")
            }
        }
    }
})
