package org.champzone.backend.coachservice.service

import java.util.*

interface NotificationClient {
    suspend fun sendEmail(id: UUID, message: String)
}