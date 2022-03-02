package dev.mbo.gitlabext.ports.redis.mapper

import dev.mbo.gitlabext.global.MapStructConfig
import dev.mbo.gitlabext.ports.redis.model.UserStatRedisEntry
import dev.mbo.gitlabext.service.model.UserStat
import org.mapstruct.InheritInverseConfiguration
import org.mapstruct.Mapper

@Mapper(config = MapStructConfig::class)
interface UserStatRedisEntityMapper {

    fun mapDomainToRedis(model: UserStat): UserStatRedisEntry

    @InheritInverseConfiguration
    fun mapRedisToDomain(hash: UserStatRedisEntry): UserStat

}