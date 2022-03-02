package dev.mbo.gitlabext

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableAsync

@EnableAsync
@SpringBootApplication
class GitlabExtApplication

fun main(args: Array<String>) {
    runApplication<GitlabExtApplication>(*args)
}
