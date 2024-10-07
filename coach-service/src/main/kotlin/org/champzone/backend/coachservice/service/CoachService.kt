package org.champzone.backend.coachservice.service

import org.champzone.backend.coachservice.model.Coach
import org.champzone.backend.coachservice.model.TrainingRequest
import org.champzone.backend.coachservice.repository.CoachRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class CoachService(
    private val coachRepository: CoachRepository,
    private val coachProducer: CoachProducer
) {

    @Transactional
    fun createTraining(coachId: UUID, name: String) {
        val coach = coachRepository.findById(coachId)
            .orElseThrow { IllegalArgumentException("Coach with id $coachId not found") }

        val trainingRequest = TrainingRequest(null, name, coachId)
        coachProducer.sendCreateTrainingRequest(trainingRequest)
    }

    @Transactional
    fun cancelTraining(coachId: UUID, trainingId: UUID) {
        val coach = coachRepository.findById(coachId)
            .orElseThrow { IllegalArgumentException("Coach with id $coachId not found") }

        val trainingRequest = TrainingRequest(trainingId, null, coachId)
        coachProducer.sendCancelTrainingRequest(trainingRequest)
    }

    fun createCoach(firstName: String, lastName: String, email: String, rating: Double): Coach {
        val coach = Coach(
            firstName = firstName,
            lastName = lastName,
            email = email,
            rating = rating,
        )
        return coachRepository.save(coach)
    }

    fun deleteCoach(coachId: UUID) {
        if (coachRepository.existsById(coachId)) {
            coachRepository.deleteById(coachId)
        } else {
            throw Exception("Coach with ID $coachId not found.")
        }
    }

    fun getAllCoaches(): List<Coach> {
        return coachRepository.findAll()
    }

    fun getCoachById(id: UUID): Coach? {
        return coachRepository.findById(id).orElse(null)
    }
}
