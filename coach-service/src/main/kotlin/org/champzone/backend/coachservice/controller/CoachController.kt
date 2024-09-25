package org.champzone.backend.coachservice.controller

import org.champzone.backend.coachservice.service.CoachService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.beans.factory.annotation.Autowired
import java.util.UUID

@RestController
@RequestMapping("/coach")
class CoachController @Autowired constructor(private val coachService: CoachService) {

    @PostMapping("/training/create")
    fun createTraining(@RequestParam coachId: UUID, @RequestParam name: String): ResponseEntity<String> {
        return try {
            coachService.createTraining(coachId, name)
            ResponseEntity.ok("Training creation request sent.")
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(e.message)
        }
    }

    @PostMapping("/training/cancel")
    fun cancelTraining(@RequestParam coachId: UUID, @RequestParam trainingId: UUID): ResponseEntity<String> {
        return try {
            coachService.cancelTraining(coachId, trainingId)
            ResponseEntity.ok("Training cancel request sent.")
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(e.message)
        }
    }
}