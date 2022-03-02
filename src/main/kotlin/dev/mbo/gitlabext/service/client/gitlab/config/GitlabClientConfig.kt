package dev.mbo.gitlabext.service.client.gitlab.config

import org.gitlab4j.api.GitLabApi
import org.gitlab4j.api.systemhooks.SystemHookListener
import org.gitlab4j.api.systemhooks.SystemHookManager
import org.gitlab4j.api.webhook.WebHookListener
import org.gitlab4j.api.webhook.WebHookManager
import org.slf4j.LoggerFactory
import org.slf4j.event.Level
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class GitlabClientConfig {

    private val log = LoggerFactory.getLogger(javaClass)

    @Bean
    fun gitLabApi(
        @Value("\${gitlab.serverUrl}") gitlabServerUrl: String,
        @Value("\${gitlab.personalAccessToken}") gitlabPersonalAccessToken: String,
    ): GitLabApi {
        return GitLabApi(gitlabServerUrl, gitlabPersonalAccessToken)
            .withRequestResponseLogging(Level.TRACE)
    }

    @Bean
    fun webHookManager(
        @Value("\${gitlab.hookToken}") gitlabHookToken: String,
        listeners: List<WebHookListener>,
    ): WebHookManager {
        val manager = WebHookManager(gitlabHookToken)
        listeners.forEach {
            log.debug("registering WebHookListener: {}", it.javaClass.name)
            manager.addListener(it)
        }
        return manager
    }

    @Bean
    fun systemHookManager(
        @Value("\${gitlab.hookToken}") gitlabHookToken: String,
        listeners: List<SystemHookListener>,
    ): SystemHookManager {
        val manager = SystemHookManager(gitlabHookToken)
        listeners.forEach {
            log.debug("registering SystemHookListener: {}", it.javaClass.name)
            manager.addListener(it)
        }
        return manager
    }

}