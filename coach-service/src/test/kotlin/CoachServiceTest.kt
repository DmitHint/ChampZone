package org.champzone.backend.coachservice.test

import io.mockk.*
import org.champzone.backend.coachservice.model.Coach
import org.champzone.backend.coachservice.repository.CoachRepository
import org.champzone.backend.coachservice.service.CoachProducer
import org.champzone.backend.coachservice.service.CoachService
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import java.util.*

@SpringBootTest
//@SpringBootTest(classes = arrayOf(CoachRepository::class),
//    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CoachServiceTest {

    @MockBean
    private lateinit var coachRepository: CoachRepository

    @Autowired
    private lateinit var coachProducer: CoachProducer

    @Autowired
    private lateinit var coachService: CoachService

    @BeforeEach
    fun setUp() {
        coachService = CoachService(coachRepository, coachProducer)
    }

    @Test
    fun `test createCoach should save coach`() {
        val firstName = "John"
        val lastName = "Doe"
        val email = "john.doe@example.com"
        val rating = 4.5
        val coach = Coach(firstName = firstName, lastName = lastName, email = email, rating = rating)

        every { coachRepository.save(any<Coach>()) } returns coach

        val savedCoach = coachService.createCoach(firstName, lastName, email, rating)

        assertNotNull(savedCoach)
        assertEquals(firstName, savedCoach.firstName)
        assertEquals(lastName, savedCoach.lastName)
        assertEquals(email, savedCoach.email)

        verify { coachRepository.save(any<Coach>()) }
    }

    @Test
    fun `test deleteCoach should delete coach if exists`() {
        val coachId = UUID.randomUUID()

        every { coachRepository.existsById(coachId) } returns true
        every { coachRepository.deleteById(coachId) } just Runs

        assertDoesNotThrow { coachService.deleteCoach(coachId) }

        verify { coachRepository.existsById(coachId) }
        verify { coachRepository.deleteById(coachId) }
    }

    @Test
    fun `test deleteCoach should throw exception if coach does not exist`() {
        val coachId = UUID.randomUUID()

        every { coachRepository.existsById(coachId) } returns false

        val exception = assertThrows<Exception> { coachService.deleteCoach(coachId) }

        assertEquals("Coach with ID $coachId not found.", exception.message)
        verify { coachRepository.existsById(coachId) }
        verify(exactly = 0) { coachRepository.deleteById(coachId) }
    }

    @Test
    fun `test createTraining should send create training request`() {
        val coachId = UUID.randomUUID()
        val name = "New Training"
        val coach = Coach(id = coachId, firstName = "John", lastName = "Doe", email = "john.doe@example.com", rating = 4.5)

        every { coachRepository.findById(coachId) } returns Optional.of(coach)
        every { coachProducer.sendCreateTrainingRequest(any()) } just Runs

        assertDoesNotThrow { coachService.createTraining(coachId, name) }

        verify { coachRepository.findById(coachId) }
        verify { coachProducer.sendCreateTrainingRequest(any()) }
    }

    @Test
    fun `test cancelTraining should send cancel training request`() {
        val coachId = UUID.randomUUID()
        val trainingId = UUID.randomUUID()
        val coach = Coach(id = coachId, firstName = "John", lastName = "Doe", email = "john.doe@example.com", rating = 4.5)

        every { coachRepository.findById(coachId) } returns Optional.of(coach)
        every { coachProducer.sendCancelTrainingRequest(any()) } just Runs

        assertDoesNotThrow { coachService.cancelTraining(coachId, trainingId) }

        verify { coachRepository.findById(coachId) }
        verify { coachProducer.sendCancelTrainingRequest(any()) }
    }

    @Test
    fun `test getAllCoaches should return list of coaches`() {
        val coach1 = Coach(firstName = "John", lastName = "Doe", email = "john.doe@example.com", rating = 4.7)
        val coach2 = Coach(firstName = "Jane", lastName = "Doe", email = "jane.doe@example.com", rating = 4.5)

        every { coachRepository.findAll() } returns listOf(coach1, coach2)

        val result = coachService.getAllCoaches()
        assertEquals(2, result.size)
        assertEquals("John", result[0].firstName)
        assertEquals("Jane", result[1].firstName)

        verify { coachRepository.findAll() }
    }
}
