package org.champzone.backend.coachservice.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import java.util.UUID

@Entity
data class Coach(
    @Id
    @GeneratedValue
    val id: UUID = UUID.randomUUID(),
    val firstName: String,
    val lastName: String,
    val email: String
)
