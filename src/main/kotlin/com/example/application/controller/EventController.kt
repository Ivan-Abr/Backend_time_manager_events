package com.example.application.controller

import USER_REQUEST_KEY
import com.example.application.dto.EventCreateDTO
import com.example.application.dto.EventUpdateDTO
import com.example.application.entity.Event
import com.example.application.service.EventService
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("timemanager/event")
class EventController(private var eventService: EventService) {

    @GetMapping()
    fun getAllEvents(@RequestAttribute(USER_REQUEST_KEY) userId: UUID): List<Event> {
        return eventService.getAllEvents(userId)
    }

    @GetMapping(path = ["{eventId}"])
    fun getEventById(@RequestAttribute(USER_REQUEST_KEY) userId: UUID, @PathVariable("eventId") eventId: Long): Optional<Event> {
        return eventService.findEventById(userId, eventId)
    }

    @PostMapping
    fun registerNewEvent(@RequestAttribute(USER_REQUEST_KEY) userId: UUID, @RequestBody eventCreateDTO: EventCreateDTO) {
//        eventService.createNewEvent(userId, event)
    }

    @PatchMapping(path = ["{eventId}"])
    fun updateEvent(@PathVariable("eventId") eventId: Long, @RequestBody eventUpdateDTO: EventUpdateDTO) {

    }


//    @PutMapping(path = ["{eventId}/name"])
//    fun changeEventName(@PathVariable("eventId") eventId: Long,
//                      @RequestParam(required = false) eventName: String){
//        eventService.updateEventName(eventId, eventName)
//    }
//
//
//    @PutMapping(path = ["{eventId}/desc"])
//    fun changeEventDesc(@PathVariable("eventId") eventId: Long,
//                      @RequestParam(required = false) eventDesc: String){
//        eventService.updateEventDesc(eventId, eventDesc)
//    }
//
//    @PutMapping(path = ["{eventId}/date"])
//    fun changeEventDate(@PathVariable("eventId") eventId: Long,
//                        @RequestParam(required = false) eventDate: LocalDate){
//        eventService.updateEventDate(eventId, eventDate)
//    }
//
//
//
//    @PutMapping(path = ["{eventId}/tag/{tagId}"])
//    fun assignTagToEvent(@PathVariable("eventId") eventId: Long,
//                                  @PathVariable("tagId") tagId: Long):Event?{
//        return eventService.assignTagstoEvent(eventId, tagId)
//    }
//
//    @DeleteMapping(path = ["{eventId}"])
//    fun deleteEventById(@PathVariable("eventId") eventId:Long){
//        return eventService.deleteEventById(eventId)
//    }
}