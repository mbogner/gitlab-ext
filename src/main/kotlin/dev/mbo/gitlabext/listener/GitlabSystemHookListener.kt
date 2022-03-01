package dev.mbo.gitlabext.listener

import dev.mbo.gitlabext.listener.worker.AbstractSystemWorker
import org.gitlab4j.api.systemhooks.GroupMemberSystemHookEvent
import org.gitlab4j.api.systemhooks.GroupSystemHookEvent
import org.gitlab4j.api.systemhooks.KeySystemHookEvent
import org.gitlab4j.api.systemhooks.MergeRequestSystemHookEvent
import org.gitlab4j.api.systemhooks.ProjectSystemHookEvent
import org.gitlab4j.api.systemhooks.PushSystemHookEvent
import org.gitlab4j.api.systemhooks.RepositorySystemHookEvent
import org.gitlab4j.api.systemhooks.SystemHookListener
import org.gitlab4j.api.systemhooks.TagPushSystemHookEvent
import org.gitlab4j.api.systemhooks.TeamMemberSystemHookEvent
import org.gitlab4j.api.systemhooks.UserSystemHookEvent
import org.springframework.stereotype.Component

@Component
class GitlabSystemHookListener(
    private val projectSystemHookEventWorker: AbstractSystemWorker<ProjectSystemHookEvent>,
    private val teamMemberSystemHookEventWorker: AbstractSystemWorker<TeamMemberSystemHookEvent>,
    private val userSystemHookEventWorker: AbstractSystemWorker<UserSystemHookEvent>,
    private val keySystemHookEventWorker: AbstractSystemWorker<KeySystemHookEvent>,
    private val groupSystemHookEventWorker: AbstractSystemWorker<GroupSystemHookEvent>,
    private val groupMemberSystemHookEventWorker: AbstractSystemWorker<GroupMemberSystemHookEvent>,
    private val pushSystemHookEventWorker: AbstractSystemWorker<PushSystemHookEvent>,
    private val tagPushSystemHookEventWorker: AbstractSystemWorker<TagPushSystemHookEvent>,
    private val repositorySystemHookEventWorker: AbstractSystemWorker<RepositorySystemHookEvent>,
    private val mergeRequestSystemHookEventWorker: AbstractSystemWorker<MergeRequestSystemHookEvent>,
) : SystemHookListener {

    override fun onProjectEvent(event: ProjectSystemHookEvent) {
        projectSystemHookEventWorker.process(event)
    }

    override fun onTeamMemberEvent(event: TeamMemberSystemHookEvent) {
        teamMemberSystemHookEventWorker.process(event)
    }

    override fun onUserEvent(event: UserSystemHookEvent) {
        userSystemHookEventWorker.process(event)
    }

    override fun onKeyEvent(event: KeySystemHookEvent) {
        keySystemHookEventWorker.process(event)
    }

    override fun onGroupEvent(event: GroupSystemHookEvent) {
        groupSystemHookEventWorker.process(event)
    }

    override fun onGroupMemberEvent(event: GroupMemberSystemHookEvent) {
        groupMemberSystemHookEventWorker.process(event)
    }

    override fun onPushEvent(event: PushSystemHookEvent) {
        pushSystemHookEventWorker.process(event)
    }

    override fun onTagPushEvent(event: TagPushSystemHookEvent) {
        tagPushSystemHookEventWorker.process(event)
    }

    override fun onRepositoryEvent(event: RepositorySystemHookEvent) {
        repositorySystemHookEventWorker.process(event)
    }

    override fun onMergeRequestEvent(event: MergeRequestSystemHookEvent) {
        mergeRequestSystemHookEventWorker.process(event)
    }
}