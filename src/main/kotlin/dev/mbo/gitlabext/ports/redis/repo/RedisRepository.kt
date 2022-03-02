package dev.mbo.gitlabext.ports.redis.repo

import camelToSnakeCase
import com.fasterxml.jackson.databind.ObjectMapper
import dev.mbo.gitlabext.ports.redis.model.RedisEntry
import dev.mbo.gitlabext.service.exc.NotFoundException
import org.slf4j.LoggerFactory
import org.springframework.data.redis.core.RedisTemplate
import javax.validation.ConstraintViolationException
import javax.validation.Validator

/**
 * Prefixes:
 *
 * e... entry: Used for normal database entries.
 * i... index: Index with references for another entry.
 */
abstract class RedisRepository<I, V : RedisEntry>(
    protected val redisTemplate: RedisTemplate<String, String>,
    private val objectMapper: ObjectMapper,
    private val validator: Validator,
) {

    private val log = LoggerFactory.getLogger(javaClass)
    private val keyPrefix: String by lazy { keyPrefix().value }
    private val valueClass: Class<V> by lazy { valueClass() }
    protected val setName: String by lazy { composeSetName(valueClass) }

    open fun save(id: I, value: V): V {
        value.inPreValidate()
        validate(value)
        val key = composeKey(id!!)
        redisTemplate.opsForValue().set(key, objectMapper.writeValueAsString(value))
        return value
    }

    open fun findById(id: I): V? {
        val valueStr = redisTemplate.opsForValue().get(composeKey(id!!)) ?: throw NotFoundException(
            "no ${valueClass.simpleName} found with id $id",
            mapOf("${valueClass.simpleName.camelToSnakeCase()}_id" to id)
        )
        return objectMapper.readValue(valueStr, valueClass)
    }

    open fun deleteById(id: I): V {
        val key = composeKey(id!!)
        val valueStr = redisTemplate.opsForValue().getAndDelete(key) ?: throw NotFoundException(
            "no ${valueClass.simpleName} found for id $id",
            mapOf("${valueClass.simpleName.camelToSnakeCase()}_id" to id)
        )
        return objectMapper.readValue(valueStr, valueClass)
    }

    abstract fun valueClass(): Class<V>

    protected fun assertExistence(key: String, message: String, args: Map<String, Any?>) {
        if (!redisTemplate.hasKey(key)) {
            throw NotFoundException(message, args)
        }
    }

    private fun validate(obj: Any?) {
        if (null == obj) {
            throw ConstraintViolationException(
                "tried to validate null instead of ${valueClass().simpleName}",
                emptySet()
            )
        }
        val constraintViolations = validator.validate(obj)
        if (constraintViolations.isNotEmpty()) {
            log.warn("validation of {} failed: {}", constraintViolations)
            throw ConstraintViolationException("${valueClass().simpleName} failed validation", constraintViolations)
        }
    }

    open fun keyPrefix(): RedisKeyPrefix {
        return RedisKeyPrefix.ENTRY
    }

    open fun composeKey(id: Any, myKeyPrefix: String = keyPrefix, mySetName: String = setName): String {
        if (myKeyPrefix.isBlank()) {
            throw IllegalArgumentException("myKeyPrefix must not be blank")
        }
        return "$myKeyPrefix:$mySetName:$id"
    }

    open fun composeSetName(valueClass: Class<*>): String {
        return valueClass.simpleName.replace(RedisEntry::class.java.simpleName, "")
            .camelToSnakeCase()
    }

}