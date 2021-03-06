package dev.mbo.gitlabext.ports.redis.model

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.Instant
import java.util.UUID

data class UserRedisEntry(
    @get:JsonProperty("id")
    var id: UUID,
    @get:JsonProperty("gitlab_id")
    var gitlabId: Int,
    @get:JsonProperty("name")
    var name: String,
    @get:JsonProperty("username")
    var username: String,
    @get:JsonProperty("avatar_url")
    var avatarUrl: String?,
    @get:JsonProperty("email")
    var email: String?,
    @get:JsonProperty("time")
    var time: Instant? = null,
    @get:JsonProperty("millis")
    var millis: Long? = null,
) : RedisEntry {
    override fun inPreValidate() {
        val now = Instant.now()
        this.time = now
        this.millis = now.toEpochMilli()
    }
}