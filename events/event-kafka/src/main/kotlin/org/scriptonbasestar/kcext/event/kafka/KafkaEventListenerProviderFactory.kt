package org.scriptonbasestar.kcext.event.kafka

import org.jboss.logging.Logger
import org.keycloak.Config
import org.keycloak.events.EventListenerProvider
import org.keycloak.events.EventListenerProviderFactory
import org.keycloak.models.KeycloakSession
import org.keycloak.models.KeycloakSessionFactory
import java.io.IOException
import java.util.concurrent.TimeoutException

class KafkaEventListenerProviderFactory : EventListenerProviderFactory {

    private val logger: Logger = Logger.getLogger(KafkaEventListenerProviderFactory::class.java)
    private lateinit var kafkaClient: KafkaClient

    override fun create(session: KeycloakSession): EventListenerProvider {
        return KafkaEventListenerProvider(kafkaClient, session)
    }

    override fun init(config: Config.Scope) {
        val kafkaConfig = KafkaConfig()
        kafkaClient = KafkaClient(kafkaConfig)
    }

    override fun postInit(factory: KeycloakSessionFactory?) {
    }

    override fun close() {
        try {
            kafkaClient.close()
        } catch (e: IOException) {
            logger.error(
                "keycloak-to-kafka ERROR on close",
                e
            )
        } catch (e: TimeoutException) {
            logger.error(
                "keycloak-to-kafka ERROR on close",
                e
            )
        }
    }

    override fun getId(): String {
        return "keycloak-to-kafka"
    }
}
