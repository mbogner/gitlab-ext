package dev.mbo.gitlabext

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class GitlabExtApplication

fun main(args: Array<String>) {
	runApplication<GitlabExtApplication>(*args)
}
