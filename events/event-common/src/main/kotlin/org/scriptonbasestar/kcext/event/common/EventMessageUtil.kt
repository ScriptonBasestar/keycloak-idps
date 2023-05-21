package org.scriptonbasestar.kcext.event.common

import org.keycloak.events.Event
import org.keycloak.events.admin.AdminEvent
import java.util.regex.Pattern

object EventMessageUtil {
    const val ROUTING_KEY_PREFIX = "KK.EVENT"
    private val SPECIAL_CHARACTERS = Pattern.compile("[^*#a-zA-Z0-9 _.-]")
    private val SPACE = Pattern.compile(" ")
    private val DOT = Pattern.compile("\\.")

    fun calculateRoutingKey(adminEvent: AdminEvent): String {
        // KK.EVENT.ADMIN.<REALM>.<RESULT>.<RESOURCE_TYPE>.<OPERATION>
        val routingKey: String = (
            ROUTING_KEY_PREFIX +
                ".ADMIN" +
                "." + removeDots(adminEvent.realmId) +
                "." + (if (adminEvent.error != null) "ERROR" else "SUCCESS") +
                "." + adminEvent.resourceTypeAsString +
                "." + adminEvent.operationType.toString()
            )
        return normalizeKey(routingKey)
    }

    fun calculateRoutingKey(event: Event): String {
        // KK.EVENT.CLIENT.<REALM>.<RESULT>.<CLIENT>.<EVENT_TYPE>
        val routingKey: String = (
            ROUTING_KEY_PREFIX +
                ".CLIENT" +
                "." + removeDots(event.realmId) +
                "." + (if (event.error != null) "ERROR" else "SUCCESS") +
                "." + removeDots(event.clientId) +
                "." + event.type
            )
        return normalizeKey(routingKey)
    }

    // Remove all characters apart a-z, A-Z, 0-9, space, underscore, eplace all spaces and hyphens with underscore
    fun normalizeKey(stringToNormalize: CharSequence): String {
        return SPACE.matcher(
            SPECIAL_CHARACTERS.matcher(
                stringToNormalize
            ).replaceAll("")
        )
            .replaceAll("_")
    }

    fun removeDots(stringToNormalize: String?): String? {
        return if (stringToNormalize != null) {
            DOT.matcher(stringToNormalize).replaceAll("")
        } else {
            null
        }
    }
}
