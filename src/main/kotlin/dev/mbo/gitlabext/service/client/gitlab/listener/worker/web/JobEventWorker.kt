package dev.mbo.gitlabext.service.client.gitlab.listener.worker.web

import dev.mbo.gitlabext.service.client.gitlab.listener.worker.AbstractWebWorker
import org.gitlab4j.api.webhook.JobEvent
import org.springframework.stereotype.Component

@Component
class JobEventWorker : AbstractWebWorker<JobEvent>() {

}