package dev.mbo.gitlabext.listener.worker.web

import dev.mbo.gitlabext.listener.worker.AbstractWebWorker
import org.gitlab4j.api.webhook.WikiPageEvent
import org.springframework.stereotype.Component

@Component
class WikiPageEventWorker : AbstractWebWorker<WikiPageEvent>() {

}