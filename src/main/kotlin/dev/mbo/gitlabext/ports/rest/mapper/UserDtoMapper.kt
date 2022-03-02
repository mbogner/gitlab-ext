package dev.mbo.gitlabext.ports.rest.mapper

import dev.mbo.gitlabext.global.MapStructConfig
import dev.mbo.gitlabext.ports.rest.model.UserDto
import dev.mbo.gitlabext.service.model.User
import org.mapstruct.InheritInverseConfiguration
import org.mapstruct.Mapper

@Mapper(config = MapStructConfig::class)
interface UserDtoMapper {

    fun mapDtoToDomain(dto: UserDto): User

    @InheritInverseConfiguration
    fun mapDomainToDto(model: User): UserDto

}