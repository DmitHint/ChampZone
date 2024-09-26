package org.champzone.backend.coachservice.service

import org.champzone.backend.coachservice.model.TrainingRequest
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class CoachProducer(private val kafkaTemplate: KafkaTemplate<String, TrainingRequest>) {

    fun sendCreateTrainingRequest(trainingRequest: TrainingRequest) {
        kafkaTemplate.send("create-training", trainingRequest)
    }

    fun sendCancelTrainingRequest(trainingRequest: TrainingRequest) {
        kafkaTemplate.send("cancel-training", trainingRequest)
    }
}
