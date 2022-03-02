package dev.mbo.gitlabext.service

import dev.mbo.gitlabext.ports.redis.mapper.UserStatRedisEntityMapper
import dev.mbo.gitlabext.ports.redis.repo.UserStatRedisRepository
import dev.mbo.gitlabext.service.exc.NotFoundException
import dev.mbo.gitlabext.service.model.User
import dev.mbo.gitlabext.service.model.UserStat
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class UserStatService(
    private val redisRepository: UserStatRedisRepository,
    private val mapper: UserStatRedisEntityMapper
) {

    fun save(entry: UserStat): UserStat {
        if (null == entry.id) {
            entry.id = UUID.randomUUID()
        }
        val saved = redisRepository.save(entry.id!!, mapper.mapDomainToRedis(entry))
        return mapper.mapRedisToDomain(saved)
    }

    fun read(id: UUID): UserStat {
        val hash = redisRepository.findById(id) ?: throw NotFoundException(
            "no ${User::class.java.simpleName} with id $id",
            mapOf("id" to id)
        )
        return mapper.mapRedisToDomain(hash)
    }

    fun delete(id: UUID): UserStat {
        val hash = redisRepository.deleteById(id) ?: throw NotFoundException(
            "no ${User::class.java.simpleName} with id $id",
            mapOf("id" to id)
        )
        return mapper.mapRedisToDomain(hash)
    }

}