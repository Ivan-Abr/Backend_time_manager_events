package com.example.application.controller

import com.example.application.entity.Event
import com.example.application.service.EventService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import java.util.*

@Controller
@RequestMapping("event")
class EventController(private var eventService: EventService) {

    @GetMapping()
    fun getAllEvents(): List<Event> {
        return eventService.getAllEvents()
    }

    @GetMapping(path = ["{eventId}"])
    fun getEventById(@PathVariable("eventId") eventId:Long): Optional<Event> {
        return eventService.findEventById(eventId)
    }

    @PostMapping
    fun registerNewEvent(@RequestBody event: Event){
        eventService.createNewEvent(event)
    }

    @PutMapping(path = ["{eventId}/name"])
    fun changeEventName(@PathVariable("eventId") eventId: Long,
                      @RequestParam(required = false) eventName: String){
        eventService.updateEventName(eventId, eventName)
    }


    @PutMapping(path = ["{eventId}/desc"])
    fun changeEventDesc(@PathVariable("eventId") eventId: Long,
                      @RequestParam(required = false) eventDesc: String){
        eventService.updateEventDesc(eventId, eventDesc)
    }


    @PutMapping(path = ["{eventId}/notification/{notificationId}"])
    fun assignNotificationToEvent(@PathVariable("eventId") eventId: Long,
                                 @PathVariable("notificationId") notificationId: Long):Event?{
        return eventService.assignNotestoEvent(eventId, notificationId)
    }

    @PutMapping(path = ["{eventId}/tag/{tagId}"])
    fun assignTagToEvent(@PathVariable("eventId") eventId: Long,
                                  @PathVariable("tagId") tagId: Long):Event?{
        return eventService.assignTagstoEvent(eventId, tagId)
    }

    @DeleteMapping(path = ["{eventId}"])
    fun deleteEventById(@PathVariable("eventId") eventId:Long){
        return eventService.deleteEventById(eventId)
    }
}