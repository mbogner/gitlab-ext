package dev.mbo.gitlabext.service.client.gitlab.listener.worker.web

import dev.mbo.gitlabext.service.client.gitlab.service.GitLabService
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import java.util.Date

@Service
class IssueEventWorkerService(
    val gitLabService: GitLabService,
) {

    @Async
    fun createIssueDiscussion(projectId: Int, issueIid: Int, body: String) {
        gitLabService.createIssueDiscussion(projectId, issueIid, body)
    }

    @Async
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
        gitLabService.updateIssue(
            projectId,
            issueIid,
            title,
            description,
            confidential,
            assigneeIds,
            milestoneId,
            dueDate
        )
    }

}