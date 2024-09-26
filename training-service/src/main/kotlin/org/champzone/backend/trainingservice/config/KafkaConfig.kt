package org.champzone.backend.trainingservice.config

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.champzone.backend.trainingservice.model.TrainingRequest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer
import org.springframework.kafka.support.serializer.JsonDeserializer
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory

@EnableKafka
@Configuration
class KafkaConfig {

    @Bean
    fun consumerFactory(): ConsumerFactory<String, TrainingRequest> {
        val config = mapOf(
            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to "kafka:9092",
            ConsumerConfig.GROUP_ID_CONFIG to "group_id",
            ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
            ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to JsonDeserializer::class.java
        )
        return DefaultKafkaConsumerFactory(config, StringDeserializer(), ErrorHandlingDeserializer(JsonDeserializer(
            TrainingRequest::class.java)))
    }

    @Bean
    fun kafkaListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, TrainingRequest> {
        val factory = ConcurrentKafkaListenerContainerFactory<String, TrainingRequest>()
        factory.consumerFactory = consumerFactory()
        return factory
    }
}
