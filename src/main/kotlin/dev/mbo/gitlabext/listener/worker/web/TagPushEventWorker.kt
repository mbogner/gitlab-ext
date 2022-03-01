package dev.mbo.gitlabext.listener.worker.web

import dev.mbo.gitlabext.listener.worker.AbstractWebWorker
import org.gitlab4j.api.webhook.TagPushEvent
import org.springframework.stereotype.Component

@Component
class TagPushEventWorker : AbstractWebWorker<TagPushEvent>() {

}