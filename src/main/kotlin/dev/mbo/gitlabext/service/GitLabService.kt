package dev.mbo.gitlabext.service

import org.gitlab4j.api.GitLabApi
import org.springframework.stereotype.Service

@Service
class GitLabService(
    private val gitLabClient: GitLabApi,
) {

    fun deleteAllProjectLabels(projectId: Int) {
        val projectLabels = gitLabClient.labelsApi.getProjectLabels(projectId)
        for (projectLabel in projectLabels) {
            gitLabClient.labelsApi.deleteProjectLabel(projectId, projectLabel.id)
        }
    }

}