package dev.mbo.gitlabext.listener.worker.web

import com.google.api.services.drive.Drive
import com.google.api.services.drive.model.File
import dev.mbo.gitlabext.listener.worker.AbstractWebWorker
import org.gitlab4j.api.GitLabApi
import org.gitlab4j.api.webhook.IssueEvent
import org.springframework.stereotype.Component
import java.time.Instant
import java.util.Date

@Component
class IssueEventWorker(
    private val googleDrive: Drive,
    private val gitLabApi: GitLabApi,
) : AbstractWebWorker<IssueEvent>() {

    override fun process(event: IssueEvent) {
        super.process(event)
        val createdAtPrevious = event.changes?.createdAt?.previous
        val updateAtPrevious = event.changes?.updatedAt?.previous
        if (
            null != event.changes?.createdAt && // for update the whole field is null
            null == createdAtPrevious && null == updateAtPrevious // for create both previous are null
        ) {
            create(event)
        } else {
            update(event)
        }
    }

    fun create(event: IssueEvent) {
        log.debug("was a create")

        val document = createDocument(fileName(event), getOrCreateProjectFolder(event))
        val url = "https://docs.google.com/document/d/${document.id}"

        gitLabApi.discussionsApi.createIssueDiscussion(
            event.project.id,
            event.objectAttributes.iid,
            "added google doc: $url",
            Date.from(Instant.now())
        )
        gitLabApi.issuesApi.updateIssue(
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
        log.debug("added comment to issue {}", event.objectAttributes.iid)
    }

    fun update(event: IssueEvent) {
        log.debug("was an update")
    }

    fun createDocument(name: String, folder: File): File {
        val file = File()
        file.mimeType = "application/vnd.google-apps.document"
        file.name = name
        file.parents = listOf(folder.id)
        val created = googleDrive.files().create(file)
            .setFields("id")
            .execute()
        log.debug("created file {} ({})", name, created.id)
        return created
    }

    fun getOrCreateProjectFolder(event: IssueEvent): File {
        return getOrCreateFolder(folderName(event), getOrCreateFolder("gitlab_documents"))
    }

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

    fun getOrCreateFolder(name: String, parent: File? = null): File {
        var folder = searchForFolder(name)
        if (null == folder) {
            val metadata = File()
            metadata.name = name
            metadata.mimeType = "application/vnd.google-apps.folder"
            if (null != parent) {
                log.debug("used parent folder ${parent.id}")
                metadata.parents = listOf(parent.id)
            }

            folder = googleDrive.files().create(metadata)
                .setFields("id")
                .execute()!!
            log.debug("created folder $name")
        }
        return folder
    }

    fun searchForFolder(
        name: String,
        query: String? = FOLDER_QUERY_PART,
        spaces: String = "drive"
    ): File? {
        var pageToken: String? = null
        do {
            val result = googleDrive.files().list()
                .setQ(query)
                .setSpaces(spaces)
                .setFields("nextPageToken, files(id, name)")
                .setPageToken(pageToken)
                .execute()
            for (file in result.files) {
                log.debug("Found file: {} ({})", file.name, file.id)
                if (name.equals(file.name, ignoreCase = true)) {
                    log.debug("match")
                    return file
                }
            }
            pageToken = result.nextPageToken
        } while (pageToken != null)
        return null
    }

    companion object {
        const val FOLDER_QUERY_PART = "mimeType='application/vnd.google-apps.folder'"
    }
}