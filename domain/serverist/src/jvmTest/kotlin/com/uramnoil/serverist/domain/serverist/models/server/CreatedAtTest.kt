package com.uramnoil.serverist.domain.serverist.models.server

import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import kotlinx.datetime.Clock
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

@ExperimentalTime
class CreatedAtTest : FunSpec({
    context("正常系") {
        test("現在") {
            shouldNotThrow<IllegalArgumentException> {
                CreatedAt(Clock.System.now())
            }
        }
        test("過去") {
            shouldNotThrow<IllegalArgumentException> {
                CreatedAt(Clock.System.now() - Duration.Companion.days(1))
            }
        }
    }

    context("異常系") {
        test("未来") {
            shouldThrow<IllegalArgumentException> {
                CreatedAt(Clock.System.now() + Duration.Companion.minutes(1))
            }
        }
    }
})
