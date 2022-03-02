package dev.mbo.gitlabext.ports.redis.config

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer
import java.nio.charset.StandardCharsets

@Suppress("unused") // reusable factory
class RedisTemplateFactory private constructor() {

    companion object {
        fun <K, V> create(
            connectionFactory: RedisConnectionFactory,

            keySerializer: RedisSerializer<*>,
            valueSerializer: RedisSerializer<*>,

            hashKeySerializer: RedisSerializer<*>,
            hashValueSerializer: RedisSerializer<*>,

            enableTransactionSupport: Boolean = true
        ): RedisTemplate<K, V> {
            val template = RedisTemplate<K, V>()
            template.setConnectionFactory(connectionFactory)
            template.keySerializer = keySerializer
            template.hashKeySerializer = hashKeySerializer
            template.hashValueSerializer = hashValueSerializer
            template.valueSerializer = valueSerializer
            template.setEnableTransactionSupport(enableTransactionSupport)
            template.afterPropertiesSet()
            return template
        }

        fun stringSerializer(): StringRedisSerializer {
            return StringRedisSerializer(StandardCharsets.UTF_8)
        }

        fun <T> typedJsonSerializer(
            clazz: Class<T>,
            objectMapper: ObjectMapper? = null
        ): Jackson2JsonRedisSerializer<T> {
            val serializer = Jackson2JsonRedisSerializer(clazz)
            if (null != objectMapper) {
                serializer.setObjectMapper(objectMapper)
            }
            return serializer
        }

        fun genericJsonSerializer(objectMapper: ObjectMapper? = null): RedisSerializer<Any> {
            if (null == objectMapper) {
                return GenericJackson2JsonRedisSerializer()
            }
            return GenericJackson2JsonRedisSerializer(objectMapper)
        }
    }

}