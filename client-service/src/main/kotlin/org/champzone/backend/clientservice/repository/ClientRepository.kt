package org.champzone.backend.clientservice.repository

import org.champzone.backend.clientservice.model.Client
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface ClientRepository : JpaRepository<Client, UUID>
