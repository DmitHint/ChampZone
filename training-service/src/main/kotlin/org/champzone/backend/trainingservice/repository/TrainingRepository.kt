package org.champzone.backend.trainingservice.repository

import org.champzone.backend.trainingservice.model.Training
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface TrainingRepository : JpaRepository<Training, UUID> {

//    @Procedure(value = "")
//    fun assignClient(@Param("v_client_id") name: String?)
//    fun assignCoach(@Param("v_coach_id") id: String?)

}
