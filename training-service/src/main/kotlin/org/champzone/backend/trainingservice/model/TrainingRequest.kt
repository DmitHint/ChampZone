package org.champzone.backend.trainingservice.model

import java.util.UUID

data class TrainingRequest(
    val trainingId: UUID?,
    val name: String?,
    val coachId: UUID
)