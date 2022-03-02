package dev.mbo.gitlabext.ports.rest

import dev.mbo.gitlabext.ports.rest.mapper.UserStatDtoMapper
import dev.mbo.gitlabext.ports.rest.model.UserStatDto
import dev.mbo.gitlabext.service.UserStatService
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
class UserStatController(
    private val service: UserStatService,
    private val mapper: UserStatDtoMapper,
) {

    private val log = LoggerFactory.getLogger(javaClass)

    @PostMapping(
        path = ["/user-stats"],
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun create(@Valid @RequestBody dto: UserStatDto): ResponseEntity<UserStatDto> {
        log.debug("create: {}", dto)
        dto.inPreMap()
        val user = mapper.mapDtoToDomain(dto)
        val saved = service.save(user)
        val respDto = mapper.mapDomainToDto(saved)
        return ResponseEntity.ok(respDto)
    }

    @GetMapping(
        path = ["/user-stats/{id}"],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun read(@PathVariable id: UUID): ResponseEntity<UserStatDto> {
        log.debug("read: {}", id)
        val result = service.read(id)
        val respDto = mapper.mapDomainToDto(result)
        return ResponseEntity.ok(respDto)
    }

    @DeleteMapping(
        path = ["/user-stats/{id}"],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun delete(@PathVariable id: UUID): ResponseEntity<UserStatDto> {
        log.debug("read: {}", id)
        val result = service.delete(id)
        val respDto = mapper.mapDomainToDto(result)
        return ResponseEntity.ok(respDto)
    }

}