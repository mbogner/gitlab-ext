package dev.mbo.gitlabext.service.exc

abstract class ServiceException : RuntimeException {

    @Suppress("MemberVisibilityCanBePrivate")
    val args: Map<String, Any?>

    constructor(message: String, args: Map<String, Any?> = emptyMap()) : super(message) {
        this.args = args
    }

    constructor(message: String, cause: Throwable, args: Map<String, Any?> = emptyMap()) : super(message, cause) {
        this.args = args
    }

    constructor(cause: Throwable, args: Map<String, Any?> = emptyMap()) : super(cause) {
        this.args = args
    }
}