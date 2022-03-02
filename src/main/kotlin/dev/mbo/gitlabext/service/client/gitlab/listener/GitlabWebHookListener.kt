package dev.mbo.gitlabext.service.client.gitlab.listener

import dev.mbo.gitlabext.service.client.gitlab.listener.worker.AbstractWebWorker
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
import org.springframework.stereotype.Component

@Component
class GitlabWebHookListener(
    private val buildEventWorker: AbstractWebWorker<BuildEvent>,
    private val issueEventWorker: AbstractWebWorker<IssueEvent>,
    private val jobEventWorker: AbstractWebWorker<JobEvent>,
    private val mergeRequestEventWorker: AbstractWebWorker<MergeRequestEvent>,
    private val noteEventWorker: AbstractWebWorker<NoteEvent>,
    private val pipelineEventWorker: AbstractWebWorker<PipelineEvent>,
    private val pushEventWorker: AbstractWebWorker<PushEvent>,
    private val tagPushEventWorker: AbstractWebWorker<TagPushEvent>,
    private val wikiPageEventWorker: AbstractWebWorker<WikiPageEvent>,
    private val deploymentEventWorker: AbstractWebWorker<DeploymentEvent>,
    private val releaseEventWorker: AbstractWebWorker<ReleaseEvent>,
) : WebHookListener {

    override fun onBuildEvent(event: BuildEvent) {
        buildEventWorker.process(event)
    }

    override fun onIssueEvent(event: IssueEvent) {
        issueEventWorker.process(event)
    }

    override fun onJobEvent(event: JobEvent) {
        jobEventWorker.process(event)
    }

    override fun onMergeRequestEvent(event: MergeRequestEvent) {
        mergeRequestEventWorker.process(event)
    }

    override fun onNoteEvent(event: NoteEvent) {
        noteEventWorker.process(event)
    }

    override fun onPipelineEvent(event: PipelineEvent) {
        pipelineEventWorker.process(event)
    }

    override fun onPushEvent(event: PushEvent) {
        pushEventWorker.process(event)
    }

    override fun onTagPushEvent(event: TagPushEvent) {
        tagPushEventWorker.process(event)
    }

    override fun onWikiPageEvent(event: WikiPageEvent) {
        wikiPageEventWorker.process(event)
    }

    override fun onDeploymentEvent(event: DeploymentEvent) {
        deploymentEventWorker.process(event)
    }

    override fun onReleaseEvent(event: ReleaseEvent) {
        releaseEventWorker.process(event)
    }
}