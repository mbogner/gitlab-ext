package dev.mbo.gitlabext.listener

import org.gitlab4j.api.webhook.BuildEvent
import org.gitlab4j.api.webhook.DeploymentEvent
import org.gitlab4j.api.webhook.IssueEvent
import org.gitlab4j.api.webhook.JobEvent
import org.gitlab4j.api.webhook.MergeRequestEvent
import org.gitlab4j.api.webhook.NoteEvent
import org.gitlab4j.api.webhook.PipelineEvent
import org.gitlab4j.api.webhook.PushEvent
import org.gitlab4j.api.webhook.ReleaseEvent
import org.gitlab4j.api.webhook.TagPushEvent
import org.gitlab4j.api.webhook.WebHookListener
import org.gitlab4j.api.webhook.WikiPageEvent
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class GitlabWebHookListener : WebHookListener {

    private val log = LoggerFactory.getLogger(javaClass)

    override fun onBuildEvent(event: BuildEvent) {
        log.debug("received: {}", event)
    }

    override fun onIssueEvent(event: IssueEvent) {
        log.debug("received: {}", event)
    }

    override fun onJobEvent(event: JobEvent) {
        log.debug("received: {}", event)
    }

    override fun onMergeRequestEvent(event: MergeRequestEvent) {
        log.debug("received: {}", event)
    }

    override fun onNoteEvent(event: NoteEvent) {
        log.debug("received: {}", event)
    }

    override fun onPipelineEvent(event: PipelineEvent) {
        log.debug("received: {}", event)
    }

    override fun onPushEvent(event: PushEvent) {
        log.debug("received: {}", event)
    }

    override fun onTagPushEvent(event: TagPushEvent) {
        log.debug("received: {}", event)
    }

    override fun onWikiPageEvent(event: WikiPageEvent) {
        log.debug("received: {}", event)
    }

    override fun onDeploymentEvent(event: DeploymentEvent) {
        log.debug("received: {}", event)
    }

    override fun onReleaseEvent(event: ReleaseEvent) {
        log.debug("received: {}", event)
    }
}