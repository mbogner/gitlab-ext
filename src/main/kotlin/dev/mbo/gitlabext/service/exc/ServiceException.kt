package dev.mbo.gitlabext.service.exc

abstract class ServiceException : RuntimeException {

    val errorCode: ErrorCode
    val args: Map<String, Any?>

    constructor(message: String, errorCode: ErrorCode, args: Map<String, Any?> = emptyMap()) : super(message) {
        this.args = args
        this.errorCode = errorCode
    }

    constructor(message: String, cause: Throwable, errorCode: ErrorCode, args: Map<String, Any?> = emptyMap()) : super(message, cause) {
        this.args = args
        this.errorCode = errorCode
    }

    constructor(cause: Throwable, errorCode: ErrorCode, args: Map<String, Any?> = emptyMap()) : super(cause) {
        this.args = args
        this.errorCode = errorCode
    }
}