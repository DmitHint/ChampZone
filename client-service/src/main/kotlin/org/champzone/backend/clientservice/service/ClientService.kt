package org.champzone.backend.clientservice.service

import org.champzone.backend.clientservice.model.Client
import org.champzone.backend.clientservice.repository.ClientRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class ClientService(
    private val clientRepository: ClientRepository,
) {
    fun createClient(firstName: String, lastName: String, email: String, login: String): Client {
        val client = Client(
            firstName = firstName,
            lastName = lastName,
            email = email,
            login = login,
        )
        return clientRepository.save(client)
    }

    fun deleteClient(clientId: UUID) {
        if (clientRepository.existsById(clientId)) {
            clientRepository.deleteById(clientId)
        } else {
            throw Exception("Coach with ID $clientId not found.")
        }
    }

    fun getAllClients(): List<Client> {
        return clientRepository.findAll()
    }

    fun getClientById(id: UUID): Client? {
        return clientRepository.findById(id).orElse(null)
    }
}
