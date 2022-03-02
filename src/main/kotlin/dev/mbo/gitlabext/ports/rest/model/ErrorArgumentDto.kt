package dev.mbo.gitlabext.ports.rest.model

data class ErrorArgumentDto(
    val key: String,
    val value: String?,
    val type: String,
) : Dto {
    override fun inPreMap() {}
}