package dev.mbo.gitlabext.ports.redis.repo

import com.fasterxml.jackson.databind.ObjectMapper
import dev.mbo.gitlabext.ports.redis.model.RedisEntry
import org.slf4j.LoggerFactory
import org.springframework.data.redis.core.RedisTemplate
import javax.validation.ConstraintViolationException
import javax.validation.Validator

abstract class RedisRepository<I, V : RedisEntry>(
    private val redisTemplate: RedisTemplate<String, String>,
    private val objectMapper: ObjectMapper,
    private val validator: Validator,
) {

    private val log = LoggerFactory.getLogger(javaClass)

    open fun save(id: I, value: V): V {
        value.inPreValidate()
        validate(value)

        val setName = composeSetName(valueClass())
        val key = composeKey(id!!, setName)

        redisTemplate.opsForValue().set(key, objectMapper.writeValueAsString(value))
        redisTemplate.opsForSet().add(setName, key)
        return value
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
            log.warn("validation failed: {}", constraintViolations)
            throw ConstraintViolationException("${valueClass().simpleName} failed validation", constraintViolations)
        }
    }

    open fun findById(id: I): V? {
        val key = composeKey(id!!, valueClass())
        val valueStr = redisTemplate.opsForValue().get(key)
        return objectMapper.readValue(valueStr, valueClass())
    }

    open fun deleteById(id: I): V? {
        val setName = composeSetName(valueClass())
        val key = composeKey(id!!, setName)
        val valueStr = redisTemplate.opsForValue().getAndDelete(key)
        redisTemplate.opsForSet().remove(setName, key)
        return objectMapper.readValue(valueStr, valueClass())
    }

    abstract fun valueClass(): Class<V>

    companion object {
        fun composeKey(id: Any, valueClass: Class<*>): String {
            return "${composeSetName(valueClass)}:$id"
        }

        fun composeKey(id: Any, setName: String): String {
            return "$setName:$id"
        }

        fun composeSetName(valueClass: Class<*>): String {
            return valueClass.simpleName
        }
    }

}