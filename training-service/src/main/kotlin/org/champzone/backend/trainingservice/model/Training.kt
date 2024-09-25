package org.champzone.backend.trainingservice.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import java.util.UUID

@Entity
data class Training(
    @Id
    @GeneratedValue
    val id: UUID? = UUID.randomUUID(),
    val name: String,
    val coachId: UUID
)
