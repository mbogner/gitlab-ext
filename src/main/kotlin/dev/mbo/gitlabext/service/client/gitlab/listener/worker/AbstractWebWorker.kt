package dev.mbo.gitlabext.service.client.gitlab.listener.worker

import org.gitlab4j.api.webhook.Event
import org.slf4j.Logger
import org.slf4j.LoggerFactory

abstract class AbstractWebWorker<T : Event> {

    @Suppress("MemberVisibilityCanBePrivate")
    protected val log: Logger = LoggerFactory.getLogger(javaClass)

    open fun process(event: T) {
        log.debug("received hook: {}", event)
    }

}