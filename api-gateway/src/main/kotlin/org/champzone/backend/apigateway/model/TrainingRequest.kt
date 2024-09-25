package org.champzone.backend.apigateway.model

import java.util.UUID

data class TrainingRequest(
    val trainingId: UUID?,
    val name: String?,
    val coachId: UUID
)