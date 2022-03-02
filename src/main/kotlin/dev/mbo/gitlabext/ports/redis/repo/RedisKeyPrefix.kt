package dev.mbo.gitlabext.ports.redis.repo

import org.apache.commons.lang3.StringUtils

enum class RedisKeyPrefix(val value: String) {
    ENTRY("e"),
    INDEX("i");

    fun join(path: Set<String>): String {
        return value + ":" + StringUtils.join(path, ":")
    }
}