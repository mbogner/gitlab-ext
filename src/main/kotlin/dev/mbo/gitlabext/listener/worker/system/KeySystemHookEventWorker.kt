package dev.mbo.gitlabext.listener.worker.system

import dev.mbo.gitlabext.listener.worker.AbstractSystemWorker
import org.gitlab4j.api.systemhooks.KeySystemHookEvent
import org.springframework.stereotype.Component

@Component
class KeySystemHookEventWorker : AbstractSystemWorker<KeySystemHookEvent>() {
}