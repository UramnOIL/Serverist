package com.uramnoil.serverist.backend

import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.StringRedisSerializer
import java.util.concurrent.TimeUnit
import kotlin.test.Test

class LettuceTest {
    @Test
    fun `Lettuceの使い方`() {
        val connectionFactory = LettuceConnectionFactory(RedisStandaloneConfiguration())
        connectionFactory.afterPropertiesSet()

        val redisTemplate = RedisTemplate<String, String>()
        redisTemplate.connectionFactory = connectionFactory
        redisTemplate.keySerializer = StringRedisSerializer()
        redisTemplate.valueSerializer = StringRedisSerializer()
        redisTemplate.afterPropertiesSet()

        redisTemplate.delete("hoge")
        redisTemplate.opsForValue()["hoge"] = "fuga"
        redisTemplate.opsForValue()["hoge"].also(::println)
        redisTemplate.expire("hoge", 30, TimeUnit.SECONDS)
    }
}