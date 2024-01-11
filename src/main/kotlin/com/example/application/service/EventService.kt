package com.example.application.service
import com.example.application.dto.EventCreateDTO
import com.example.application.dto.EventDTO
import com.example.application.dto.EventUpdateDTO
import com.example.application.entity.Event
import com.example.application.entity.Tag
import com.example.application.repository.EventRepo
import com.example.application.repository.TagRepo
import com.example.application.repository.UserRepo
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.util.*
import kotlin.collections.HashSet

@Service
class EventService(private var eventRepo: EventRepo,
                   private var userRepo: UserRepo,
                   private var tagRepo: TagRepo) {

    fun getAllEvents(userId: UUID): List<EventDTO> {
        val eventInstances = eventRepo.findAllByUserId(userId)
        val eventDTOList = listOf<EventDTO>().toMutableList()
        eventInstances.forEach { eventInstance ->
            val tagIdCollection : MutableList<Long> = mutableListOf()
            eventInstance.tags?.forEach {tagInstance ->
                println(tagInstance.tagId.toString())
                tagIdCollection.add(tagInstance.tagId)
            }
            eventDTOList.add(EventDTO(
                    eventId = eventInstance.eventId,
                    eventDesc = eventInstance.eventDesc,
                    eventDate = eventInstance.eventDate.toString(),
                    eventName = eventInstance.eventName,
                    tags = tagIdCollection
            ))
        }
        return eventDTOList
    }

    fun findEventById(userId: UUID, eventId: Long): EventDTO {
        if (!eventRepo.existsById(eventId))
            throw IllegalStateException("Event with id $eventId does not exists")
        val eventInstance = eventRepo.findEventByUserIdAndEventId(userId, eventId).get()
        val tagIdCollection : MutableList<Long> = mutableListOf()

        println(eventInstance.tags?.size.toString())
        eventInstance.tags?.forEach {
            println(it.tagId.toString())
            tagIdCollection.add(it.tagId)
        }

        return EventDTO(
                eventId = eventInstance.eventId,
                eventDesc = eventInstance.eventDesc,
                eventDate = eventInstance.eventDate.toString(),
                eventName = eventInstance.eventName,
                tags = tagIdCollection
        )
    }

    fun createEvent(userId: UUID, eventCreateDTO: EventCreateDTO): Event {
        val userInstance = userRepo.findById(userId).get()
        val newListTags: MutableList<Tag> = listOf<Tag>().toMutableList()
        eventCreateDTO.tags?.forEach {
            val tag = tagRepo.findById(it)
            if (!tag.isEmpty) {
                newListTags.apply { add(tag.get()) }
            }
        }
        val eventInstance = Event(
                eventName = eventCreateDTO.eventName,
                eventDate = eventCreateDTO.eventDate,
                eventDesc = eventCreateDTO.eventDesc,
                tags = newListTags,
                user = userInstance,
        )

        return eventRepo.save(eventInstance)
    }

    fun updateEvent(userId: UUID, eventId: Long, eventUpdateDTO: EventUpdateDTO): Event {
        val eventInstance = eventRepo.findById(eventId)
                .orElseThrow { java.lang.IllegalStateException("factor with id" + eventId + "does not exist") }

        if (userId.compareTo(eventInstance.user?.userId) != 0) {
            throw Exception("This user not allowed to use this event")
        }

        if (eventUpdateDTO.eventDate is LocalDate) {
            eventInstance.eventDate = eventUpdateDTO.eventDate!!
        }
        if (eventUpdateDTO.eventDesc is String) {
            eventInstance.eventDesc = eventUpdateDTO.eventDesc!!
        }
        if (eventUpdateDTO.eventName is String) {
            eventInstance.eventName = eventUpdateDTO.eventName!!
        }
        if (eventUpdateDTO.tags is Collection<*>) {
            println("here")
            val newListTags: MutableList<Tag> = listOf<Tag>().toMutableList()
            eventUpdateDTO.tags?.forEach {
                println(it.toString())
                val tag = tagRepo.findById(it)
                println(tag)
                if (!tag.isEmpty) {
                    newListTags.apply { add(tag.get()) }
                }
            }
            eventInstance.tags = newListTags
        }

        return eventRepo.save(eventInstance)
    }

    fun deleteEventById(userId: UUID, eventId: Long) {
        val eventInstance = eventRepo.findById(eventId)
                .orElseThrow { java.lang.IllegalStateException("factor with id" + eventId + "does not exist") }

        if (userId.compareTo(eventInstance.user?.userId) != 0) {
            throw Exception("This user not allowed to use this event")
        }

        return eventRepo.deleteById(eventId)
    }

}