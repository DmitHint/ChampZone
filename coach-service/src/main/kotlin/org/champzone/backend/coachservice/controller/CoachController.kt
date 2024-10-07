package org.champzone.backend.coachservice.controller

import org.champzone.backend.coachservice.model.Coach
import org.champzone.backend.coachservice.service.CoachService
import org.springframework.http.ResponseEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.util.UUID


@RestController
@RequestMapping("/coach")
class CoachController @Autowired constructor(private val coachService: CoachService) {

    @PostMapping("/create")
    fun createCoach(
        @RequestParam firstName: String,
        @RequestParam lastName: String,
        @RequestParam email: String,
        @RequestParam rating: Double
    ): ResponseEntity<Coach> {
        return try {
            val coach = coachService.createCoach(firstName, lastName, email, rating)
            ResponseEntity.ok(coach)
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(null)
        }
    }

    @DeleteMapping("/delete")
    fun deleteCoach(@RequestParam coachId: UUID): ResponseEntity<String> {
        return try {
            coachService.deleteCoach(coachId)
            ResponseEntity.ok("Coach with ID $coachId has been deleted.")
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(e.message)
        }
    }

    @GetMapping("/all")
    fun getAllCoaches(): ResponseEntity<List<Coach>> {
        return try {
            val coaches = coachService.getAllCoaches()
            ResponseEntity.ok(coaches)
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(emptyList())
        }
    }

    @GetMapping("/{id}")
    fun getCoachById(@PathVariable id: UUID): ResponseEntity<Coach> {
        return try {
            val coach = coachService.getCoachById(id)
            if (coach != null) {
                ResponseEntity.ok(coach)
            } else {
                ResponseEntity.notFound().build()
            }
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(null)
        }
    }

}
