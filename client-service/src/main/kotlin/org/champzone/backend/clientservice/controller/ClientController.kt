package org.champzone.backend.clientservice.controller

import org.champzone.backend.clientservice.model.Client
import org.champzone.backend.clientservice.service.ClientService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.UUID


@RestController
@RequestMapping("/client")
class ClientController @Autowired constructor(private val clientService: ClientService) {

    @GetMapping("/all")
    fun getAllClients(): ResponseEntity<List<Client>> {
        return try {
            val clients = clientService.getAllClients()
            ResponseEntity.ok(clients)
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(emptyList())
        }
    }

    @GetMapping("/{id}")
    fun getClientById(@PathVariable id: UUID): ResponseEntity<Client> {
        return try {
            val client = clientService.getClientById(id)
            if (client != null) {
                ResponseEntity.ok(client)
            } else {
                ResponseEntity.notFound().build()
            }
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(null)
        }
    }

    @PostMapping("/create")
    fun createCoach(
        @RequestParam firstName: String,
        @RequestParam lastName: String,
        @RequestParam email: String,
        @RequestParam login: String
    ): ResponseEntity<Client> {
        return try {
            val client = clientService.createClient(
                firstName, lastName, email, login,
            )
            ResponseEntity.ok(client)
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(null)
        }
    }

    @DeleteMapping("/delete")
    fun deleteCoach(@RequestParam clientId: UUID): ResponseEntity<String> {
        return try {
            clientService.deleteClient(clientId)
            ResponseEntity.ok("Client with ID $clientId has been deleted.")
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(e.message)
        }
    }

}
