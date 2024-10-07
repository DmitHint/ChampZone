package org.champzone.backend.apigateway.model

data class ClientDto(
    val id: Long?,
    val login: String,
    val firstName: String,
    val lastName: String,
    var token: String? = null
)
