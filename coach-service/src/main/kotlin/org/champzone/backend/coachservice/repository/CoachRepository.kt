package org.champzone.backend.coachservice.repository

import org.champzone.backend.coachservice.model.Coach
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface CoachRepository : JpaRepository<Coach, UUID>
