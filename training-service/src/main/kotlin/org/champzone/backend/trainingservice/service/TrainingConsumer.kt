package org.champzone.backend.trainingservice.service

import org.champzone.backend.trainingservice.model.Training
import org.champzone.backend.trainingservice.model.TrainingRequest
import org.champzone.backend.trainingservice.repository.TrainingRepository
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TrainingConsumer(private val trainingRepository: TrainingRepository) {

    @KafkaListener(topics = ["create-training"], groupId = "group_id")
    @Transactional
    fun createTraining(trainingRequest: TrainingRequest) {
        val training = Training(
            id = null,
            name = trainingRequest.name ?: throw IllegalArgumentException("Training name cannot be null"),
            coachId = trainingRequest.coachId
        )
        trainingRepository.save(training)
    }

    @KafkaListener(topics = ["cancel-training"], groupId = "group_id")
    @Transactional
    fun cancelTraining(trainingRequest: TrainingRequest) {
        val training = trainingRepository.findById(trainingRequest.trainingId!!)
            .orElseThrow { IllegalArgumentException("Training with id ${trainingRequest.trainingId} not found") }
        trainingRepository.delete(training)
    }
}
