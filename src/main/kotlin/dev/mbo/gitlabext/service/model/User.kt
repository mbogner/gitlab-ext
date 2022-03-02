package dev.mbo.gitlabext.service.model

import java.time.Instant
import java.util.UUID
import javax.validation.constraints.NotBlank

data class User(
    var id: UUID?,

    var gitlabId: Int? = null,

    @get:NotBlank
    var name: String,

    @get:NotBlank
    var username: String,

    var avatarUrl: String? = null,

    var email: String? = null,

    var time: Instant? = null,

    var millis: Long? = null,
) : Domain