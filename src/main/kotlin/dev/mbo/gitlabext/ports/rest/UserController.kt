package dev.mbo.gitlabext.ports.rest

import dev.mbo.gitlabext.ports.rest.mapper.UserDtoMapper
import dev.mbo.gitlabext.ports.rest.model.UserDto
import dev.mbo.gitlabext.service.UserService
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.UUID
import javax.validation.Valid

@RestController
class UserController(
    private val service: UserService,
    private val mapper: UserDtoMapper,
) {

    private val log = LoggerFactory.getLogger(javaClass)

    @PostMapping(
        path = ["/users"],
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun create(@Valid @RequestBody dto: UserDto): ResponseEntity<UserDto> {
        log.debug("create: {}", dto)
        dto.inPreMap()
        val user = mapper.mapDtoToDomain(dto)
        val saved = service.save(user)
        val respDto = mapper.mapDomainToDto(saved)
        return ResponseEntity.ok(respDto)
    }

    @GetMapping(
        path = ["/users/{id}"],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun read(@PathVariable id: UUID): ResponseEntity<UserDto> {
        log.debug("read: {}", id)
        val result = service.read(id)
        val respDto = mapper.mapDomainToDto(result)
        return ResponseEntity.ok(respDto)
    }

    @DeleteMapping(
        path = ["/users/{id}"],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun delete(@PathVariable id: UUID): ResponseEntity<UserDto> {
        log.debug("read: {}", id)
        val result = service.delete(id)
        val respDto = mapper.mapDomainToDto(result)
        return ResponseEntity.ok(respDto)
    }

}