package dev.mbo.gitlabext.ports.redis.repo

import com.fasterxml.jackson.databind.ObjectMapper
import dev.mbo.gitlabext.ports.redis.model.UserRedisEntry
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import java.util.UUID
import javax.validation.Validator

@Component
class UserRedisRepository(
    redisTemplate: RedisTemplate<String, String>,
    objectMapper: ObjectMapper,
    validator: Validator,
) : RedisRepository<UUID, UserRedisEntry>(redisTemplate, objectMapper, validator) {

    override fun valueClass(): Class<UserRedisEntry> {
        return UserRedisEntry::class.java
    }

}