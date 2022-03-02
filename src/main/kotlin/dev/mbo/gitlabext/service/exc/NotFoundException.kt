package dev.mbo.gitlabext.service.exc

class NotFoundException : ServiceException {

    constructor(message: String, args: Map<String, Any?> = emptyMap()) : super(
        message,
        ApiErrorCode.NOT_FOUND,
        args
    )

    constructor(message: String, cause: Throwable, args: Map<String, Any?> = emptyMap()) : super(
        message,
        cause,
        ApiErrorCode.NOT_FOUND,
        args
    )

    constructor(cause: Throwable, args: Map<String, Any?> = emptyMap()) : super(
        cause,
        ApiErrorCode.NOT_FOUND,
        args
    )
}