package dev.mbo.gitlabext.service.client.gitlab.listener.worker.system

import dev.mbo.gitlabext.service.client.gitlab.listener.worker.AbstractSystemWorker
import org.gitlab4j.api.systemhooks.KeySystemHookEvent
import org.springframework.stereotype.Component

@Component
class KeySystemHookEventWorker : AbstractSystemWorker<KeySystemHookEvent>() {
}