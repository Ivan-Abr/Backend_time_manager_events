package com.example.application.service

import com.example.application.entity.Event
import com.example.application.entity.Notification
import com.example.application.entity.Tag
import com.example.application.repository.EventRepo
import com.example.application.repository.NotificationRepo
import com.example.application.repository.TagRepo
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.util.*

@Service
class EventService(private var eventRepo: EventRepo,
    private var notificationRepo: NotificationRepo,
    private var tagRepo: TagRepo) {

    fun getAllEvents():List<Event>{
        return eventRepo.findAll()
    }

    fun findEventById(eventId: Long): Optional<Event> {
        if (!eventRepo.existsById(eventId))
            throw IllegalStateException("Event with id $eventId does not exists")
        return eventRepo.findById(eventId)
    }

    fun createNewEvent(event: Event):Event{
        if (eventRepo.findEventByName(event.eventName)!=null) {
            throw IllegalStateException("Event with name ${event.eventName} already exists")
        }
        return eventRepo.save(event)
    }

    fun updateEventName(eventId: Long, eventName:String):Event?{
        val event = eventRepo.findById(eventId)
            .orElseThrow { java.lang.IllegalStateException("factor with id" + eventId + "does not exist") }

        if (eventName != null && eventName.isNotEmpty() && event.eventName!= eventName)
            event.eventName = eventName
        return eventRepo.save(event)
    }

    fun updateEventDesc(eventId: Long, eventDesc:String):Event?{
        val event = eventRepo.findById(eventId)
            .orElseThrow { java.lang.IllegalStateException("factor with id" + eventId + "does not exist") }

        if (eventDesc != null && eventDesc.isNotEmpty() && event.eventDesc!= eventDesc)
            event.eventDesc = eventDesc
        return eventRepo.save(event)
    }

    fun updateEventDate(eventId: Long, eventDate: LocalDate):Event?{
        val event = eventRepo.findById(eventId)
            .orElseThrow { java.lang.IllegalStateException("factor with id" + eventId + "does not exist") }

        if (eventDate!= null  && event.eventDate!= eventDate)
            event.eventDate = eventDate
        return eventRepo.save(event)
    }
    
    fun assignNotestoEvent(eventId: Long, noteId: Long):Event?{
        val event = eventRepo.findById(eventId).get()
        val notification = notificationRepo.findById(noteId).get()
        event.notes = (event.notes as MutableSet<Notification>).apply {  add(notification)}
        return eventRepo.save(event)

    }


    fun assignTagstoEvent(eventId: Long, tagId: Long): Event?{
        val event = eventRepo.findById(eventId).get()
        val tag = tagRepo.findById(tagId).get()
        event.tags = (event.tags as MutableSet<Tag>).apply { add(tag) }
        return eventRepo.save(event)
    }

    fun deleteEventById(eventId: Long){
        if (!eventRepo.existsById(eventId))
            throw IllegalStateException("Event with id $eventId does not exists")
        return eventRepo.deleteById(eventId)
    }

}