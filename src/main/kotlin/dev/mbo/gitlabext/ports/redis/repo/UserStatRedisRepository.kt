package dev.mbo.gitlabext.ports.redis.repo

import camelToSnakeCase
import com.fasterxml.jackson.databind.ObjectMapper
import dev.mbo.gitlabext.ports.redis.model.UserRedisEntry
import dev.mbo.gitlabext.ports.redis.model.UserStatRedisEntry
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import java.util.UUID
import javax.validation.Validator

@Component
class UserStatRedisRepository(
    redisTemplate: RedisTemplate<String, String>,
    objectMapper: ObjectMapper,
    validator: Validator,
) : RedisRepository<UUID, UserStatRedisEntry>(redisTemplate, objectMapper, validator) {

    private val userSetName: String by lazy { composeSetName(UserRedisEntry::class.java) }

    override fun save(id: UUID, value: UserStatRedisEntry): UserStatRedisEntry {
        assertExistenceOfUser(value)
        val entry = super.save(id, value)
        val keys = createKeys(entry)
        if (null != keys) {
            redisTemplate.opsForSet().add(keys.userKey, keys.myKey)
        }
        return entry
    }

    private fun assertExistenceOfUser(value: UserStatRedisEntry) {
        val otherName = UserRedisEntry::class.java.simpleName.camelToSnakeCase()
        assertExistence(
            composeKey(value.userId, RedisKeyPrefix.ENTRY.value, userSetName),
            "can not create stats for non existent $otherName ${value.userId}",
            mapOf("user_id" to value.userId)
        )
    }

    override fun deleteById(id: UUID): UserStatRedisEntry {
        val entry = super.deleteById(id)
        val keys = createKeys(entry)
        if (null != keys) {
            redisTemplate.opsForSet().remove(keys.userKey, keys.myKey)
        }
        return entry
    }

    data class Keys(
        val userKey: String,
        val myKey: String,
    )

    fun createKeys(entry: UserStatRedisEntry): Keys? {
        return Keys(
            userKey = composeKey(
                entry.userId,
                RedisKeyPrefix.INDEX.join(setOf(setName)),
                userSetName
            ),
            myKey = composeKey(entry.id!!)
        )
    }

    override fun valueClass(): Class<UserStatRedisEntry> {
        return UserStatRedisEntry::class.java
    }

}