package org.champzone.backend.trainingservice.controller

import org.champzone.backend.trainingservice.repository.TrainingRepository
import org.champzone.backend.trainingservice.model.Training
import org.springframework.web.bind.annotation.*
import java.util.Optional
import java.util.UUID

@RestController
@RequestMapping("/training")
class TrainingController(private val trainingRepository: TrainingRepository) {

    @GetMapping("/{id}")
    fun getTraining(@PathVariable id: UUID): Optional<Training> = trainingRepository.findById(id)
}
