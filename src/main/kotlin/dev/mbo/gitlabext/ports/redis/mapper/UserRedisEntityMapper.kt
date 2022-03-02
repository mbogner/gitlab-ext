package dev.mbo.gitlabext.ports.redis.mapper

import dev.mbo.gitlabext.global.MapStructConfig
import dev.mbo.gitlabext.ports.redis.model.UserRedisEntry
import dev.mbo.gitlabext.service.model.User
import org.mapstruct.InheritInverseConfiguration
import org.mapstruct.Mapper

@Mapper(config = MapStructConfig::class)
interface UserRedisEntityMapper {

    fun mapDomainToRedis(model: User): UserRedisEntry

    @InheritInverseConfiguration
    fun mapRedisToDomain(hash: UserRedisEntry): User

}