package dev.mbo.gitlabext.ports.rest.model

import java.time.Instant

data class ErrorDto(
    val time: Instant,
    val code: String,
    val title: String,
    val message: String,
    val args: List<ErrorArgumentDto>,
) : Dto {
    override fun inPreMap() {}
}
