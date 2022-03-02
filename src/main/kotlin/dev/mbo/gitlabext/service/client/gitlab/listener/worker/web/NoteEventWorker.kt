package dev.mbo.gitlabext.service.client.gitlab.listener.worker.web

import dev.mbo.gitlabext.service.client.gitlab.listener.worker.AbstractWebWorker
import org.gitlab4j.api.webhook.NoteEvent
import org.springframework.stereotype.Component

@Component
class NoteEventWorker : AbstractWebWorker<NoteEvent>() {

}