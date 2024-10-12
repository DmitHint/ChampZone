package org.champzone.backend.coachservice.service

import io.grpc.ManagedChannelBuilder
import org.champzone.backend.coachservice.model.Coach
import org.champzone.backend.coachservice.repository.CoachRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class GrpcNotificationClient(
    private val coachRepository: CoachRepository,
    @Value("\${grpc.notification-service.port}") private val grpcPort: Int,
    @Value("\${grpc.notification-service.host}") private val grpcHost: String
) : NotificationClient {
    private val channel = ManagedChannelBuilder.forAddress(grpcHost, grpcPort)
        .usePlaintext()
        .build()

    private val stub = NotificationServiceGrpcKt.NotificationServiceCoroutineStub(channel)

    override suspend fun sendEmail(id: UUID, message: String) {
        val coach: Coach = coachRepository.findById(id).orElseThrow {
            IllegalArgumentException("Coach with id $id not found")
        }
        val email = coach.email
        val request = EmailRequest.newBuilder()
            .setEmail(email)
            .setMessage(message)
            .build()
        try {
            val response = stub.sendEmail(request)
            if (response.success) {
                println("Email successfully sent!")
            } else {
                println("Error during sending email: ${response.error}")
            }
        } catch (e: Exception) {
            println("Error with gRPC call: ${e.message}")
        }
    }
}
