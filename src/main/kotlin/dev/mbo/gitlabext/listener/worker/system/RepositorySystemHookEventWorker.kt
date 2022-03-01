package dev.mbo.gitlabext.listener.worker.system

import dev.mbo.gitlabext.listener.worker.AbstractSystemWorker
import org.gitlab4j.api.systemhooks.RepositorySystemHookEvent
import org.springframework.stereotype.Component

@Component
class RepositorySystemHookEventWorker : AbstractSystemWorker<RepositorySystemHookEvent>() {
}