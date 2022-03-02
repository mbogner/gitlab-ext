package dev.mbo.gitlabext.ports.redis.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate


@Configuration
class RedisTemplateConfig(
    private val connectionFactory: RedisConnectionFactory,
) {

    @Bean
    fun redisTemplate(): RedisTemplate<String, String> {
        return RedisTemplateFactory.create(
            connectionFactory = connectionFactory,
            keySerializer = RedisTemplateFactory.stringSerializer(),
            valueSerializer = RedisTemplateFactory.stringSerializer(),
            hashKeySerializer = RedisTemplateFactory.stringSerializer(),
            hashValueSerializer = RedisTemplateFactory.stringSerializer(),
        )
    }

}