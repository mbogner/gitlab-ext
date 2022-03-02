package dev.mbo.gitlabext.service.client.gitlab.listener.worker.web

import dev.mbo.gitlabext.service.client.gitlab.listener.worker.AbstractWebWorker
import org.gitlab4j.api.webhook.ReleaseEvent
import org.springframework.stereotype.Component

@Component
class ReleaseEventWorker : AbstractWebWorker<ReleaseEvent>() {

}