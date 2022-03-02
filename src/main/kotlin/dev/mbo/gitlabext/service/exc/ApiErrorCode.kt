package dev.mbo.gitlabext.service.exc

import org.springframework.http.HttpStatus

enum class ApiErrorCode(
    private val code: Int,
    private val status: HttpStatus,
) : ErrorCode {
    GENERIC_ERROR(ErrorCodeValidator.validateCode(100000), HttpStatus.INTERNAL_SERVER_ERROR),
    BAD_REQUEST(ErrorCodeValidator.validateCode(100001), HttpStatus.BAD_REQUEST),
    NOT_FOUND(ErrorCodeValidator.validateCode(100002), HttpStatus.NOT_FOUND),
    BAD_PROPERTY(ErrorCodeValidator.validateCode(100003), HttpStatus.BAD_REQUEST),
    PARAMETER_TYPE_MISMATCH(ErrorCodeValidator.validateCode(100004), HttpStatus.BAD_REQUEST),
    ;

    companion object {
        private const val TITLE = "TITLE"
        private const val MESSAGE = "MESSAGE"
    }

    override fun getCode(): Int {
        return code
    }

    override fun getTitleKey(): String {
        return toPropertyKey(TITLE)
    }

    override fun getMessageKey(): String {
        return toPropertyKey(MESSAGE)
    }

    override fun getHttpStatus(): HttpStatus {
        return status
    }

    private fun toPropertyKey(key: String): String {
        return "${code}.${name}.${key}"
    }

}