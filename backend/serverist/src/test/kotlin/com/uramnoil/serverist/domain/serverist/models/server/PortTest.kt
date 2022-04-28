package com.uramnoil.serverist.domain.serverist.models.server

import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.core.spec.style.FunSpec

class PortTest : FunSpec({
    context("正常系") {
        test("0") {
            shouldNotThrow<IllegalArgumentException> {
                Port(0u)
            }
        }

        test("65535") {
            shouldNotThrow<IllegalArgumentException> {
                Port(65535u)
            }
        }
    }
})
