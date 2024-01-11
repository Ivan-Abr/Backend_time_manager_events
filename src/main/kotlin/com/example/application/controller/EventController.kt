package com.example.application.controller

import USER_REQUEST_KEY
import com.example.application.dto.EventCreateDTO
import com.example.application.dto.EventDTO
import com.example.application.dto.EventUpdateDTO
import com.example.application.dto.GetAllEventDTO
import com.example.application.entity.Event
import com.example.application.service.EventService
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("timemanager/event")
class EventController(private var eventService: EventService) {

    @GetMapping()
    fun getAllEvents(
            @RequestAttribute(USER_REQUEST_KEY) userId: UUID,
            @RequestParam("perPage") perPage: Optional<Int>,
            @RequestParam("page") page: Optional<Int>,
            @RequestParam("simpleFilter") simpleFilter: Optional<String>
    ): GetAllEventDTO {
        return eventService.getAllEvents(userId,perPage, page, simpleFilter)
    }

    @GetMapping(path = ["{eventId}"])
    fun getEventById(@RequestAttribute(USER_REQUEST_KEY) userId: UUID, @PathVariable("eventId") eventId: Long): EventDTO {
        return eventService.findEventById(userId, eventId)
    }

    @PostMapping
    fun registerEvent(@RequestAttribute(USER_REQUEST_KEY) userId: UUID, @RequestBody eventCreateDTO: EventCreateDTO): Event {
        return eventService.createEvent(userId, eventCreateDTO)
    }

    @PatchMapping(path = ["{eventId}"])
    fun updateEvent(@RequestAttribute(USER_REQUEST_KEY) userId: UUID, @PathVariable("eventId") eventId: Long, @RequestBody eventUpdateDTO: EventUpdateDTO): Event {
        return eventService.updateEvent(userId, eventId, eventUpdateDTO)
    }

    @DeleteMapping(path = ["{eventId}"])
    fun deleteEventById(@RequestAttribute(USER_REQUEST_KEY) userId: UUID, @PathVariable("eventId") eventId: Long) {
        return eventService.deleteEventById(userId, eventId)
    }
}