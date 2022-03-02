package dev.mbo.gitlabext.ports.rest.mapper

import dev.mbo.gitlabext.global.MapStructConfig
import dev.mbo.gitlabext.ports.rest.model.UserStatDto
import dev.mbo.gitlabext.service.model.UserStat
import org.mapstruct.InheritInverseConfiguration
import org.mapstruct.Mapper

@Mapper(config = MapStructConfig::class)
interface UserStatDtoMapper {

    fun mapDtoToDomain(dto: UserStatDto): UserStat

    @InheritInverseConfiguration
    fun mapDomainToDto(domain: UserStat): UserStatDto

}