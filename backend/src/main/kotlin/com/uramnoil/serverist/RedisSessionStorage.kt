package com.uramnoil.serverist

import io.ktor.sessions.*
import io.ktor.util.cio.*
import io.ktor.utils.io.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import org.springframework.data.redis.core.RedisTemplate

class RedisSessionStorage(private val redisTemplate: RedisTemplate<String, ByteArray>) : SessionStorage {
    override suspend fun write(id: String, provider: suspend (ByteWriteChannel) -> Unit) {
        coroutineScope {
            val channel = writer(Dispatchers.IO, autoFlush = true) {
                provider(channel)
            }.channel

            redisTemplate.opsForValue()[id] = channel.toByteArray()
        }
    }

    override suspend fun <R> read(id: String, consumer: suspend (ByteReadChannel) -> R): R =
        redisTemplate.opsForValue()[id]?.let { data -> consumer(ByteReadChannel(data)) }
            ?: throw NoSuchElementException("Session $id not found")

    override suspend fun invalidate(id: String) {
        redisTemplate.delete(id)
    }
}