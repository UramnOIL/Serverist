package com.uramnoil.serverist.auth.domain.kernel.model

import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec

class EmailTest : FunSpec({
    context("正常系") {
        test("hoge@example.com") {
            shouldNotThrow<IllegalArgumentException> {
                com.uramnoil.serverist.auth.domain.models.Email("hoge@example.com")
            }
        }
        test("Abc.123@example.com") {
            shouldNotThrow<IllegalArgumentException> {
                com.uramnoil.serverist.auth.domain.models.Email("Abc.123@example.com")
            }
        }
        test("user+mailbox/department=shipping@example.com") {
            shouldNotThrow<IllegalArgumentException> {
                com.uramnoil.serverist.auth.domain.models.Email("user+mailbox/department=shipping@example.com")
            }
        }
        test("!#$%&'*+-/=?^_`.{|}~@example.com") {
            shouldNotThrow<IllegalArgumentException> {
                com.uramnoil.serverist.auth.domain.models.Email("!#$%&'*+-/=?^_`.{|}~@example.com")
            }
        }
        xcontext("RFCで定められた形式だけど対応しなくてもいい形式") {
            test("\"Abc@def\"@example.com") {
                shouldNotThrow<IllegalArgumentException> {
                    com.uramnoil.serverist.auth.domain.models.Email("\"Abc@def\"@example.com")
                }
            }
            test("\"Fre\\ Bloggs\"@example.com") {
                shouldNotThrow<IllegalArgumentException> {
                    com.uramnoil.serverist.auth.domain.models.Email("\"Fre\\ Bloggs\"@example.com")
                }
            }
            test("\"Joe.\\Blow\"@example.com") {
                shouldNotThrow<IllegalArgumentException> {
                    com.uramnoil.serverist.auth.domain.models.Email("\"Joe.\\Blow\"@example.com")
                }
            }
        }
    }
    context("異常系") {
        test("hoge") {
            shouldThrow<IllegalArgumentException> {
                com.uramnoil.serverist.auth.domain.models.Email("hoge")
            }
        }
    }
})
