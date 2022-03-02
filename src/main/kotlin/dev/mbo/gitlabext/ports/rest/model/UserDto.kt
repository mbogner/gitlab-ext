package dev.mbo.gitlabext.ports.rest.model

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.Instant
import java.util.UUID
import javax.validation.constraints.NotBlank

data class UserDto(
    var id: UUID? = null,
    @get:JsonProperty("gitlab_id")
    var gitlabId: Int? = null,
    @get:NotBlank
    @get:JsonProperty("name")
    var name: String,
    @get:NotBlank
    @get:JsonProperty("username")
    var username: String,
    @get:JsonProperty("avatar_url")
    var avatarUrl: String? = null,
    @get:JsonProperty("email")
    var email: String? = null,
    var time: Instant? = null,
    var millis: Long? = null,
) : Dto {

    override fun inPreMap() {
        time = null
        millis = null
    }

}