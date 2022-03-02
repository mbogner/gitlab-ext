package dev.mbo.gitlabext.service.client.gitlab.listener.worker.web

import com.google.api.services.drive.model.File
import dev.mbo.gitlabext.service.client.google.config.service.GoogleDriveService
import dev.mbo.gitlabext.service.client.gitlab.listener.worker.AbstractWebWorker
import org.gitlab4j.api.webhook.IssueEvent
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class IssueEventWorker(
    private val service: IssueEventWorkerService,
    private val googleDriveService: GoogleDriveService,
    @Value("\${google.docs.baseDir}") private val docsBaseDir: String,
) : AbstractWebWorker<IssueEvent>() {

    override fun process(event: IssueEvent) {
        super.process(event)
        // check if it was a create or update
        val createdAtPrevious = event.changes?.createdAt?.previous
        val updateAtPrevious = event.changes?.updatedAt?.previous
        val isCreate = null != event.changes?.createdAt && // for update the whole field is null
                null == createdAtPrevious && null == updateAtPrevious // for create both previous are null
        if (isCreate) {
            onCreate(event)
        } else {
            log.debug("ignore update")
        }
    }

    fun onCreate(event: IssueEvent) {
        log.debug("was a create")

        // we need to wait for this url
        val document = googleDriveService.createDocument(fileName(event), getOrCreateProjectFolder(event))
        val url = "https://docs.google.com/document/d/${document.id}"

        // and then run api requests asynchronously
        service.createIssueDiscussion(
            event.project.id,
            event.objectAttributes.iid,
            "added google doc: $url",
        )
        service.updateIssue(
            event.project.id,
            event.objectAttributes.iid,
            event.objectAttributes.title,
            event.objectAttributes.description + "\n\n$url",
            event.objectAttributes.confidential,
            event.objectAttributes.assigneeIds,
            event.objectAttributes.milestoneId,
            event.objectAttributes.dueDate
        )
    }

    fun getOrCreateProjectFolder(event: IssueEvent): File {
        return googleDriveService.getOrCreateFolder(
            folderName(event),
            googleDriveService.getOrCreateFolder(docsBaseDir)
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