package dev.mbo.gitlabext.service.exc

import org.springframework.http.HttpStatus

interface ErrorCode {
    fun getCode(): Int
    fun getTitleKey(): String
    fun getMessageKey(): String
    fun getHttpStatus(): HttpStatus
}
