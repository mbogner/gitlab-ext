package dev.mbo.gitlabext.listener

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
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class GitlabSystemHookListener : SystemHookListener {

    private val log = LoggerFactory.getLogger(javaClass)

    override fun onProjectEvent(event: ProjectSystemHookEvent) {
        log.debug("received: {}", event)
    }

    override fun onTeamMemberEvent(event: TeamMemberSystemHookEvent) {
        log.debug("received: {}", event)
    }

    override fun onUserEvent(event: UserSystemHookEvent) {
        log.debug("received: {}", event)
    }

    override fun onKeyEvent(event: KeySystemHookEvent) {
        log.debug("received: {}", event)
    }

    override fun onGroupEvent(event: GroupSystemHookEvent) {
        log.debug("received: {}", event)
    }

    override fun onGroupMemberEvent(event: GroupMemberSystemHookEvent) {
        log.debug("received: {}", event)
    }

    override fun onPushEvent(event: PushSystemHookEvent) {
        log.debug("received: {}", event)
    }

    override fun onTagPushEvent(event: TagPushSystemHookEvent) {
        log.debug("received: {}", event)
    }

    override fun onRepositoryEvent(event: RepositorySystemHookEvent) {
        log.debug("received: {}", event)
    }

    override fun onMergeRequestEvent(event: MergeRequestSystemHookEvent) {
        log.debug("received: {}", event)
    }
}