package dev.mbo.gitlabext.ports.redis.model

import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal
import java.time.Instant
import java.util.UUID

data class UserStatRedisEntry(
    @get:JsonProperty("id")
    var id: UUID?,
    @get:JsonProperty("user_id")
    var userId: UUID,
    @get:JsonProperty("name")
    var name: String,
    @get:JsonProperty("value")
    var value: BigDecimal,
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