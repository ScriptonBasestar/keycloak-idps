package org.scriptonbasestar.kcext.event.kafka

import org.keycloak.events.Event
import org.keycloak.events.admin.AdminEvent

object KeycloakMessageDto {
    data class EventAdminNotificationMessage(
        val event: AdminEvent
    )

    data class EventNotificationMessage(
        val event: Event
    )
}
