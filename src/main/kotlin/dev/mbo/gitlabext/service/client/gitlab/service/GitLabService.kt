package dev.mbo.gitlabext.service.client.gitlab.service

import org.gitlab4j.api.GitLabApi
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.Date

@Service
class GitLabService(
    val gitLabClient: GitLabApi,
) {

    private val log = LoggerFactory.getLogger(javaClass)

    fun deleteAllProjectLabels(projectId: Int) {
        val projectLabels = gitLabClient.labelsApi.getProjectLabels(projectId)
        for (projectLabel in projectLabels) {
            gitLabClient.labelsApi.deleteProjectLabel(projectId, projectLabel.id)
            log.debug("deleted project label {}", projectLabel.id)
        }
    }

    fun createIssueDiscussion(projectId: Int, issueIid: Int, body: String) {
        gitLabClient.discussionsApi.createIssueDiscussion(
            projectId,
            issueIid,
            body,
            Date.from(Instant.now())
        )
        log.debug("added comment to issue {}", issueIid)
    }

    fun updateIssue(
        projectId: Int,
        issueIid: Int,
        title: String,
        description: String,
        confidential: Boolean,
        assigneeIds: List<Int>,
        milestoneId: Int?,
        dueDate: Date?,
    ) {
        gitLabClient.issuesApi.updateIssue(
            projectId,
            issueIid,
            title,
            description,
            confidential,
            assigneeIds,
            milestoneId,
            Date.from(Instant.now()),
            dueDate
        )
        log.debug("updated issue {}", issueIid)
    }

}