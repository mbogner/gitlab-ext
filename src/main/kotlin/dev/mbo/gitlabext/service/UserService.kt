package dev.mbo.gitlabext.service

import dev.mbo.gitlabext.ports.redis.mapper.UserRedisEntityMapper
import dev.mbo.gitlabext.ports.redis.repo.UserRedisRepository
import dev.mbo.gitlabext.service.exc.NotFoundException
import dev.mbo.gitlabext.service.model.User
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class UserService(
    private val redisRepository: UserRedisRepository,
    private val userMapper: UserRedisEntityMapper
) {

    fun save(user: User): User {
        if (null == user.id) {
            user.id = UUID.randomUUID()
        }
        val saved = redisRepository.save(user.id!!, userMapper.mapDomainToRedis(user))
        return userMapper.mapRedisToDomain(saved)
    }

    fun read(id: UUID): User {
        val hash = redisRepository.findById(id) ?: throw NotFoundException(
            "no ${User::class.java.simpleName} with id $id",
            mapOf("id" to id)
        )
        return userMapper.mapRedisToDomain(hash)
    }

    fun delete(id: UUID): User {
        val hash = redisRepository.deleteById(id) ?: throw NotFoundException(
            "no ${User::class.java.simpleName} with id $id",
            mapOf("id" to id)
        )
        return userMapper.mapRedisToDomain(hash)
    }

}