package org.scriptonbasestar.kcext.event.common

import org.keycloak.events.EventType
import org.keycloak.events.admin.OperationType

object EventMessageDto {
    data class EventAdminNotificationMessage(
        val id: String,
        val realmId: String,
        val timestamp: Long,
        val authRealmId: String,
        val authClientId: String,
        val authUserId: String,
        val authIpAddress: String,
        val operationType: OperationType,
        val resourceType: String,
        val resourcePath: String
    )

    data class EventClientNotificationMessage(
        val id: String,
        val realmId: String,
        val clientId: String,
        val userId: String,
        val timestamp: Long,
        val ipAddress: String,
        val eventType: EventType
    )
}
