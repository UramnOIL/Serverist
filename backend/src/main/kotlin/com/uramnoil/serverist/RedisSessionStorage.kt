package com.uramnoil.serverist

import io.ktor.sessions.SessionStorage
import io.ktor.util.cio.toByteArray
import io.ktor.utils.io.ByteReadChannel
import io.ktor.utils.io.ByteWriteChannel
import io.ktor.utils.io.writer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.StringRedisSerializer

class RedisSessionStorage(connectionFactory: RedisConnectionFactory) : SessionStorage {
    private val redisTemplate = RedisTemplate<String, String>()

    init {
        redisTemplate.connectionFactory = connectionFactory
        redisTemplate.keySerializer = StringRedisSerializer()
        redisTemplate.valueSerializer = StringRedisSerializer()
        redisTemplate.afterPropertiesSet()
    }

    override suspend fun write(id: String, provider: suspend (ByteWriteChannel) -> Unit) {
        coroutineScope {
            val channel = writer(Dispatchers.IO, autoFlush = true) {
                provider(channel)
            }.channel

            redisTemplate.opsForValue()[id] = channel.toByteArray().toString()
        }
    }

    override suspend fun <R> read(id: String, consumer: suspend (ByteReadChannel) -> R): R =
        redisTemplate.opsForValue()[id]?.let { data -> consumer(ByteReadChannel(data)) }
            ?: throw NoSuchElementException("Session $id not found")

    override suspend fun invalidate(id: String) {
        redisTemplate.delete(id)
    }
}