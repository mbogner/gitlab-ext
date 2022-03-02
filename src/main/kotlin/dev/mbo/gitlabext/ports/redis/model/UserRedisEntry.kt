package dev.mbo.gitlabext.ports.redis.model

import java.time.Instant
import java.util.UUID

data class UserRedisEntry(
    var id: UUID,
    var gitlabId: Int,
    var name: String,
    var username: String,
    var avatar_url: String?,
    var email: String?,
    var time: Instant? = null,
    var millis: Long? = null,
) : RedisEntry {
    override fun inPreValidate() {
        val now = Instant.now()
        this.time = now
        this.millis = now.toEpochMilli()
    }
}