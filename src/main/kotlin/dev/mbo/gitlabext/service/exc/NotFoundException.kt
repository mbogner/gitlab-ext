package dev.mbo.gitlabext.service.exc

class NotFoundException : ServiceException {
    constructor(message: String, args: Map<String, Any?> = emptyMap()) : super(message, args)
    constructor(message: String, cause: Throwable, args: Map<String, Any?> = emptyMap()) : super(message, cause, args)
    constructor(cause: Throwable, args: Map<String, Any?> = emptyMap()) : super(cause, args)
}