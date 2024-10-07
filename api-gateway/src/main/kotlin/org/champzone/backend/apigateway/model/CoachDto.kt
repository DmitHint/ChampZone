package org.champzone.backend.apigateway.model

data class CoachDto(
    val id: Long?,
    val firstName: String,
    val lastName: String,
    var token: String? = null
)