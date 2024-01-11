package com.example.application.service

import com.example.application.entity.Event
import com.example.application.entity.Tag
import com.example.application.repository.EventRepo
import com.example.application.repository.TagRepo
import com.example.application.repository.UserRepo
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.util.*

@Service
class EventService(private var eventRepo: EventRepo,
                   private var userRepo: UserRepo,
                   private var tagRepo: TagRepo) {

    fun getAllEvents(userId: UUID): List<Event> {
        return eventRepo.findAllByUserId(userId)
    }

    fun findEventById(userId: UUID, eventId: Long): Optional<Event> {
        if (!eventRepo.existsById(eventId))
            throw IllegalStateException("Event with id $eventId does not exists")
        return eventRepo.findEventByUserIdAndEventId(userId, eventId)
    }

    fun createNewEvent(userId: UUID, event: Event): Event {
        val user = userRepo.findById(userId).get()
        event.user = user
        return eventRepo.save(event)
    }

    fun updateEventName(eventId: Long, eventName: String): Event? {
        val event = eventRepo.findById(eventId)
                .orElseThrow { java.lang.IllegalStateException("factor with id" + eventId + "does not exist") }

        if (eventName != null && eventName.isNotEmpty() && event.eventName != eventName)
            event.eventName = eventName
        return eventRepo.save(event)
    }

    fun updateEventDesc(eventId: Long, eventDesc: String): Event? {
        val event = eventRepo.findById(eventId)
                .orElseThrow { java.lang.IllegalStateException("factor with id" + eventId + "does not exist") }

        if (eventDesc != null && eventDesc.isNotEmpty() && event.eventDesc != eventDesc)
            event.eventDesc = eventDesc
        return eventRepo.save(event)
    }

    fun updateEventDate(eventId: Long, eventDate: LocalDate): Event? {
        val event = eventRepo.findById(eventId)
                .orElseThrow { java.lang.IllegalStateException("factor with id" + eventId + "does not exist") }

        if (eventDate != null && event.eventDate != eventDate)
            event.eventDate = eventDate
        return eventRepo.save(event)
    }


    fun assignTagstoEvent(eventId: Long, tagId: Long): Event? {
        val event = eventRepo.findById(eventId).get()
        val tag = tagRepo.findById(tagId).get()
        event.tags = (event.tags as MutableSet<Tag>).apply { add(tag) }
        return eventRepo.save(event)
    }

    fun deleteEventById(eventId: Long) {
        if (!eventRepo.existsById(eventId))
            throw IllegalStateException("Event with id $eventId does not exists")
        return eventRepo.deleteById(eventId)
    }

}