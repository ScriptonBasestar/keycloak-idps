package org.scriptonbasestar.kcext.event.kafka

import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord
import java.util.*

class KafkaClient(
    kafkaConfig: KafkaConfig
) {
    var producer: KafkaProducer<String, String> = KafkaProducer(kafkaConfig.create())

    fun send(topic: String, key: String, value: String) {
        val record = ProducerRecord(topic, key, value)
        producer.send(record)
        producer.flush()
    }

    fun close() {
        producer.close()
    }
}
