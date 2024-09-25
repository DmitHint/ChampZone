package org.champzone.backend.coachservice.model

import java.util.UUID

data class TrainingRequest(
    val trainingId: UUID?,
    val name: String?,
    val coachId: UUID
)