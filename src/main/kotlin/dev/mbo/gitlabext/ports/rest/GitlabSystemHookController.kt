package dev.mbo.gitlabext.ports.rest

import org.gitlab4j.api.webhook.WebHookManager
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest

@RestController
class GitlabSystemHookController(
    private val webHookManager: WebHookManager,
    private val httpServletRequest: HttpServletRequest,
) {

    private val log = LoggerFactory.getLogger(javaClass)

    @PostMapping(path = ["/gitlab/hook/system"])
    fun gitlabSystemHook() {
        log.debug("gitlabSystemHook")
        webHookManager.handleEvent(httpServletRequest)
    }

}