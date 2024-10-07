package org.champzone.backend.apigateway.model

import jakarta.validation.constraints.NotEmpty

data class CredentialsDto(
    @field:NotEmpty
    val login: String,

    @field:NotEmpty
    val password: CharArray

)
