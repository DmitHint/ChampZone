package org.champzone.backend.apigateway.model

import jakarta.validation.constraints.NotEmpty

data class SignUpDto(
    @field:NotEmpty
    val firstName: String,

    @field:NotEmpty
    val lastName: String,

    @field:NotEmpty
    val login: String,

    @field:NotEmpty
    val password: CharArray
)
