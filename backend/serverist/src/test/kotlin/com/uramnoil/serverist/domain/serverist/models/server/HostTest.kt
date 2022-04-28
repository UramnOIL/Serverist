package com.uramnoil.serverist.domain.serverist.models.server

import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec

class HostTest : FunSpec({
    context("正常系") {
        test("example.com") {
            shouldNotThrow<IllegalArgumentException> {
                Host("example.com")
            }
        }

        test("192.168.0.1") {
            shouldNotThrow<IllegalArgumentException> {
                Host("192.168.0.1")
            }
        }
    }

    context("異常系") {
        test("hoge") {
            shouldThrow<IllegalArgumentException> {
                Host("hoge")
            }
        }

        test("example.123") {
            shouldThrow<IllegalArgumentException> {
                Host("hoge.123")
            }
        }

        test("example.a") {
            shouldThrow<IllegalArgumentException> {
                Host("example.a")
            }
        }

        test("192.168.1") {
            shouldThrow<IllegalArgumentException> {
                Host("192.168.1")
            }
        }
    }
})
