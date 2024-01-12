package com.example.application.service

import com.example.application.dto.EventCreateDTO
import com.example.application.dto.EventUpdateDTO
import com.example.application.dto.TagUpdateDTO
import com.example.application.entity.Event
import com.example.application.entity.Tag
import com.example.application.entity.User
import com.example.application.repository.EventRepo
import com.example.application.repository.TagRepo
import com.example.application.repository.UserRepo
import jakarta.transaction.Transactional
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.any
import org.mockito.Mockito.verify
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDate
import java.util.*
import org.junit.jupiter.api.assertThrows

@SpringBootTest
class EventServiceTest {

    @Mock
    private lateinit var eventRepo: EventRepo

    @Mock
    private  lateinit var tagRepo: TagRepo

    @Mock
    private  lateinit var userRepo: UserRepo

    @InjectMocks
    private lateinit var eventService: EventService

    private lateinit var eventCreateDTO: EventCreateDTO

    private lateinit var event1: Event
    private lateinit var event2: Event



    @BeforeEach
    fun setup(){
        val userId: UUID = UUID.randomUUID()
        val userInstance = User(userId, null)
        event1 = Event(
            1L,
            "eventName1",
            "eventDesc1",
            LocalDate.now(),
            null,
            null)

        event2 = Event(
            2L,
            "eventName2",
            "eventDesc2",
            LocalDate.now(),
            null,
            userInstance)
    }



    @Test
    fun getAllEvents() {
        val userId = UUID.randomUUID()
        Mockito.`when`(eventRepo.findAllByUserId(userId)).thenReturn(listOf(event1))
        assertNotNull(eventService.getAllEvents(userId))
    }

    @Test
    fun findEventById() {
        val eventId = event1.eventId
        val userId = UUID.randomUUID()
        Mockito.`when`(eventRepo.findEventByUserIdAndEventId(userId, eventId)).thenReturn(Optional.ofNullable(event1))
        Mockito.`when`(eventRepo.existsById(eventId)).thenReturn(true)
        val eventTest = eventService.findEventById(userId,eventId)
        assertNotNull(eventTest)
    }

    @Test
    fun createEvent() {
        val userId = UUID.randomUUID()
        val eventId = 1L
        val eventCreateDTO = EventCreateDTO("eventName","eventDesc", LocalDate.now(),null)
        val userInstance = User(userId, null)
        Mockito.`when`(userRepo.findById(userId)).thenReturn(Optional.of(userInstance))
        val saveEventInstance = Event(eventId, eventCreateDTO.eventName, eventCreateDTO.eventDesc, eventCreateDTO.eventDate, null, userInstance)
        Mockito.`when`(eventRepo.save(any())).thenReturn(saveEventInstance)
        val createdEvent = eventService.createEvent(userId, eventCreateDTO)
        assertEquals(saveEventInstance, createdEvent)
    }


    @Test
    @Transactional
    fun updateEvent() {

        val eventId = 1L
        val userId = UUID.randomUUID()
        val eventUpdateDTO = EventUpdateDTO("newEventName","newEventDesc", LocalDate.now())
        val userInstance = User(userId, null)
        val eventInstance = Event(eventId, "eventName","eventDesc", LocalDate.now(), null, userInstance)
        Mockito.`when`(eventRepo.findById(eventId)).thenReturn(Optional.of(eventInstance))
        val newEventInstance = Event(
            event1.eventId,
            eventUpdateDTO.eventName!!,
            eventUpdateDTO.eventDesc!!,
            eventUpdateDTO.eventDate!!,
            null,
            userInstance)
        Mockito.`when`(eventRepo.save(any())).thenReturn(newEventInstance)
        val updatedEvent = eventService.updateEvent(userId, eventId, eventUpdateDTO)
        assertEquals(newEventInstance, updatedEvent)


    }

    @Test
    @Transactional
    fun updateEvent_ThrowsException_WhenUserNotAllowed() {
        val eventId = 1L
        val userId = UUID.randomUUID()
        val eventUpdateDTO = EventUpdateDTO("newEventName","newEventDesc", LocalDate.now())
        val userInstance = User(UUID.randomUUID(), null)
        val eventInstance = Event(eventId, "eventName","eventDesc", LocalDate.now(), null, userInstance)
        Mockito.`when`(eventRepo.findById(eventId)).thenReturn(Optional.of(eventInstance))
        assertThrows<Exception> { eventService.updateEvent(userId, eventId, eventUpdateDTO ) }
    }

    @Test
    @Transactional
    fun updateEvent_ThrowsException_WhenTagNotFound() {
        val userId = UUID.randomUUID()
        val eventId = 1L
        val eventUpdateDTO = EventUpdateDTO("newEventName","newEventDesc", LocalDate.now())
        Mockito.`when`(eventRepo.findById(eventId)).thenReturn(Optional.empty())
        assertThrows<IllegalStateException> { eventService.updateEvent(userId, eventId, eventUpdateDTO) }
    }


    @Test
    fun deleteEventById() {
        val userId = UUID.randomUUID()
        val eventId = 1L
        val userInstance = User(userId, null)
        val eventInstance = Event(eventId, "eventName","eventDesc", LocalDate.now(), null, userInstance)
        Mockito.`when`(eventRepo.findById(eventId)).thenReturn(Optional.of(eventInstance))
        eventService.deleteEventById(userId, eventId)
        verify(eventRepo).findById(eventId)
        verify(eventRepo).deleteById(eventId)
    }

    @Test
    fun deleteEventById_ThrowsException_WhenEventNotFound(){
        val userId = UUID.randomUUID()
        val eventId = 1L
        Mockito.`when`(eventRepo.findById(eventId)).thenReturn(Optional.empty())
        assertThrows<IllegalStateException> { eventService.deleteEventById(userId, eventId) }
    }

    @Test
    fun deleteEventById_ThrowsException_ThrowsException_WhenUserNotAllowed(){
        val userId = UUID.randomUUID()
        val eventId = 1L
        val userInstance = User(UUID.randomUUID(), null)
        val eventInstance = Event(eventId, "eventName","eventDesc", LocalDate.now(), null, userInstance)
        Mockito.`when`(eventRepo.findById(eventId)).thenReturn(Optional.of(eventInstance))
        assertThrows<Exception> { eventService.deleteEventById(userId, eventId) }
    }
    
    
}