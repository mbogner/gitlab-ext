package dev.mbo.gitlabext.listener.worker

import org.gitlab4j.api.systemhooks.SystemHookEvent
import org.slf4j.Logger
import org.slf4j.LoggerFactory

abstract class AbstractSystemWorker<T : SystemHookEvent> {

    @Suppress("MemberVisibilityCanBePrivate")
    protected val log: Logger = LoggerFactory.getLogger(javaClass)

    open fun process(event: T) {
        log.debug("received hook: {}", event)
    }

}