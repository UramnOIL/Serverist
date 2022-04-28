package com.uramnoil.serverist.serverist.domain.models.server

import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec

class HostTest : FunSpec({
    context("正常系") {
        test("example.com") {
            shouldNotThrow<IllegalArgumentException> {
                com.uramnoil.serverist.serverist.domain.models.server.Host("example.com")
            }
        }

        test("192.168.0.1") {
            shouldNotThrow<IllegalArgumentException> {
                com.uramnoil.serverist.serverist.domain.models.server.Host("192.168.0.1")
            }
        }
    }

    context("異常系") {
        test("hoge") {
            shouldThrow<IllegalArgumentException> {
                com.uramnoil.serverist.serverist.domain.models.server.Host("hoge")
            }
        }

        test("example.123") {
            shouldThrow<IllegalArgumentException> {
                com.uramnoil.serverist.serverist.domain.models.server.Host("hoge.123")
            }
        }

        test("example.a") {
            shouldThrow<IllegalArgumentException> {
                com.uramnoil.serverist.serverist.domain.models.server.Host("example.a")
            }
        }

        test("192.168.1") {
            shouldThrow<IllegalArgumentException> {
                com.uramnoil.serverist.serverist.domain.models.server.Host("192.168.1")
            }
        }
    }
})
