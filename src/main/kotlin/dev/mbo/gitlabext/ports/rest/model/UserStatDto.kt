package dev.mbo.gitlabext.ports.rest.model

import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal
import java.time.Instant
import java.util.UUID

data class UserStatDto(
    @get:JsonProperty("id")
    var id: UUID?,
    @get:JsonProperty("user_id")
    var userId: UUID?,
    @get:JsonProperty("name")
    var name: String,
    @get:JsonProperty("value")
    var value: BigDecimal,
    @get:JsonProperty("time")
    var time: Instant? = null,
    @get:JsonProperty("millis")
    var millis: Long? = null,
) : Dto {

    override fun inPreMap() {
        time = null
        millis = null
    }

}