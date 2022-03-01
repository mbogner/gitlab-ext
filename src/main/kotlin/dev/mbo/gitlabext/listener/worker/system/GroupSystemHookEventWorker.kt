package dev.mbo.gitlabext.listener.worker.system

import dev.mbo.gitlabext.listener.worker.AbstractSystemWorker
import org.gitlab4j.api.systemhooks.GroupSystemHookEvent
import org.springframework.stereotype.Component

@Component
class GroupSystemHookEventWorker : AbstractSystemWorker<GroupSystemHookEvent>() {
}