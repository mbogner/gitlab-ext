package dev.mbo.gitlabext.listener.worker.system

import dev.mbo.gitlabext.listener.worker.AbstractSystemWorker
import org.gitlab4j.api.systemhooks.MergeRequestSystemHookEvent
import org.springframework.stereotype.Component

@Component
class MergeRequestSystemHookEventWorker : AbstractSystemWorker<MergeRequestSystemHookEvent>() {
}