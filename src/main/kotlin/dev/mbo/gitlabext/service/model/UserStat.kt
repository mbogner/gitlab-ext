package dev.mbo.gitlabext.service.model

import java.math.BigDecimal
import java.time.Instant
import java.util.UUID
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class UserStat(
    @get:NotNull
    var id: UUID?,
    @get:NotNull
    var userId: UUID,
    @get:NotBlank
    var name: String,
    @get:NotNull
    var value: BigDecimal,
    @get:NotNull
    var time: Instant? = null,
    @get:NotNull
    var millis: Long? = null,
) : Domain