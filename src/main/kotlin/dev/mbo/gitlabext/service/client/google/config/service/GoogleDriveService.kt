package dev.mbo.gitlabext.service.client.google.config.service

import com.google.api.services.drive.Drive
import com.google.api.services.drive.model.File
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class GoogleDriveService(
    private val googleDrive: Drive,
) {

    fun createDocument(name: String, folder: File): File {
        val file = File()
        file.mimeType = DOCUMENT_MIME_TYPE
        file.name = name
        file.parents = listOf(folder.id)
        val created = googleDrive.files().create(file)
            .setFields("id")
            .execute()
        LOG.debug("created file {} ({})", name, created.id)
        return created
    }

    fun getOrCreateFolder(name: String, parent: File? = null): File {
        var folder = searchForFolder(name)
        if (null == folder) {
            val metadata = File()
            metadata.name = name
            metadata.mimeType = FOLDER_MIME_TYPE
            if (null != parent) {
                LOG.debug("used parent folder ${parent.id}")
                metadata.parents = listOf(parent.id)
            }

            folder = googleDrive.files().create(metadata)
                .setFields("id")
                .execute()!!
            LOG.debug("created folder $name")
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
                LOG.debug("Found file: {} ({})", file.name, file.id)
                if (name.equals(file.name, ignoreCase = true)) {
                    LOG.debug("match {}", file.name)
                    return file
                }
            }
            pageToken = result.nextPageToken
        } while (pageToken != null)
        return null
    }

    companion object {
        const val FOLDER_MIME_TYPE = "application/vnd.google-apps.folder"
        const val DOCUMENT_MIME_TYPE = "application/vnd.google-apps.document"
        const val FOLDER_QUERY_PART = "mimeType='$FOLDER_MIME_TYPE'"

        private val LOG = LoggerFactory.getLogger(GoogleDriveService::class.java)
    }

}