package org.scriptonbasestar.kcext.event.kafka

import org.jboss.logging.Logger
import org.keycloak.events.Event
import org.keycloak.events.EventListenerProvider
import org.keycloak.events.EventListenerTransaction
import org.keycloak.events.admin.AdminEvent
import org.keycloak.models.KeycloakSession
import org.keycloak.util.JsonSerialization
import org.scriptonbasestar.kcext.event.common.EventMessageDto
import org.scriptonbasestar.kcext.event.common.EventMessageUtil

class KafkaEventListenerProvider(
    private val kafkaClient: KafkaClient,
    session: KeycloakSession
) : EventListenerProvider {

    private val logger: Logger = Logger.getLogger(KafkaEventListenerProvider::class.java)

    private val tx = EventListenerTransaction(this::logAdminEvent, this::logEvent)

    init {
        session.transactionManager.enlistAfterCompletion(tx)
    }

    private fun fromAdminEvent(adminEvent: AdminEvent) = EventMessageDto.EventAdminNotificationMessage(
        id = adminEvent.id,
        realmId = adminEvent.realmId,
        timestamp = adminEvent.time,
        authRealmId = adminEvent.authDetails.realmId,
        authClientId = adminEvent.authDetails.clientId,
        authUserId = adminEvent.authDetails.userId,
        authIpAddress = adminEvent.authDetails.ipAddress,
        operationType = adminEvent.operationType,
        resourceType = adminEvent.resourceTypeAsString,
        resourcePath = adminEvent.resourcePath
    )

    private fun fromClientEvent(event: Event) = EventMessageDto.EventClientNotificationMessage(
        id = event.id,
        realmId = event.realmId,
        clientId = event.clientId,
        userId = event.userId,
        timestamp = event.time,
        ipAddress = event.ipAddress,
        eventType = event.type
    )

    private fun logAdminEvent(adminEvent: AdminEvent, includeRepresentation: Boolean) {
        val message = fromAdminEvent(adminEvent)
        val routingKey: String = EventMessageUtil.calculateRoutingKey(adminEvent)
        val messageString: String = JsonSerialization.writeValueAsString(message)

        kafkaClient.send(routingKey, adminEvent.realmId, messageString)
    }
    private fun logEvent(event: Event) {
        val message = fromClientEvent(event)
        val routingKey: String = EventMessageUtil.calculateRoutingKey(event)
        val messageString: String = JsonSerialization.writeValueAsString(message)

        kafkaClient.send(routingKey, event.realmId, messageString)
    }

    override fun close() {
    }

    override fun onEvent(event: Event) {
        tx.addEvent(event)
    }

    override fun onEvent(adminEvent: AdminEvent, includeRepresentation: Boolean) {
        tx.addAdminEvent(adminEvent, includeRepresentation)
    }
}
