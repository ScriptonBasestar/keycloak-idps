package org.scriptonbasestar.kcext.event.kafka

import com.fasterxml.jackson.databind.ser.std.StringSerializer
import org.apache.kafka.clients.producer.ProducerConfig
import java.util.*

class KafkaConfig(
    val bootstrapServers: String = "localhost:9091",
    val useTls: Boolean = false,
    val acks: String = "all"
) {
    fun create(): Properties = Properties().apply {
        setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers)
        setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer::class.java.name)
        setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer::class.java.name)
        if (useTls) {
            setProperty(ProducerConfig.SECURITY_PROVIDERS_CONFIG, "SSL")
        } else {
            setProperty(ProducerConfig.SECURITY_PROVIDERS_CONFIG, "PLAINTEXT")
        }
        setProperty(ProducerConfig.ACKS_CONFIG, acks)
//        setProperty(ProducerConfig.RETRIES_CONFIG, "0")
//        setProperty(ProducerConfig.BATCH_SIZE_CONFIG, "16384")
    }
}
