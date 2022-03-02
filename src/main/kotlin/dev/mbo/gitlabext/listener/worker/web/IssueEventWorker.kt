package dev.mbo.gitlabext.listener.worker.web

import com.google.api.services.drive.model.File
import dev.mbo.gitlabext.listener.worker.AbstractWebWorker
import dev.mbo.gitlabext.service.GoogleDriveService
import org.gitlab4j.api.GitLabApi
import org.gitlab4j.api.webhook.IssueEvent
import org.springframework.stereotype.Component
import java.time.Instant
import java.util.Date

@Component
class IssueEventWorker(
    private val gitLabClient: GitLabApi,
    private val googleDriveService: GoogleDriveService,
) : AbstractWebWorker<IssueEvent>() {

    override fun process(event: IssueEvent) {
        super.process(event)
        // check if it was a create or update
        val createdAtPrevious = event.changes?.createdAt?.previous
        val updateAtPrevious = event.changes?.updatedAt?.previous
        val isCreate = null != event.changes?.createdAt && // for update the whole field is null
                null == createdAtPrevious && null == updateAtPrevious // for create both previous are null
        if (isCreate) {
            create(event)
        }
    }

    fun create(event: IssueEvent) {
        log.debug("was a create")

        val document = googleDriveService.createDocument(fileName(event), getOrCreateProjectFolder(event))
        val url = "https://docs.google.com/document/d/${document.id}"

        gitLabClient.discussionsApi.createIssueDiscussion(
            event.project.id,
            event.objectAttributes.iid,
            "added google doc: $url",
            Date.from(Instant.now())
        )
        log.debug("added comment to issue {}", event.objectAttributes.iid)
        gitLabClient.issuesApi.updateIssue(
            event.project.id,
            event.objectAttributes.iid,
            event.objectAttributes.title,
            event.objectAttributes.description + "\n\n$url",
            event.objectAttributes.confidential,
            event.objectAttributes.assigneeIds,
            event.objectAttributes.milestoneId,
            event.labels,
            null,
            event.objectAttributes.updatedAt,
            event.objectAttributes.dueDate
        )
        log.debug("updated issue description {}", event.objectAttributes.iid)
    }

    fun getOrCreateProjectFolder(event: IssueEvent): File {
        return googleDriveService.getOrCreateFolder(
            folderName(event),
            googleDriveService.getOrCreateFolder("gitlab_documents")
        )
    }

    companion object {
        fun fileName(event: IssueEvent): String {
            return "${event.project.name}_${event.objectAttributes.iid}"
                .lowercase()
                .replace(" ", "_")
                .replace("-", "_")
        }

        fun folderName(event: IssueEvent): String {
            return event.project.name
                .lowercase()
                .replace(" ", "_")
                .replace("-", "_")
        }
    }
}