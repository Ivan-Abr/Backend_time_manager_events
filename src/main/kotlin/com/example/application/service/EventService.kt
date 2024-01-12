package com.example.application.service

import com.example.application.dto.*
import com.example.application.entity.Event
import com.example.application.entity.Tag
import com.example.application.repository.EventRepo
import com.example.application.repository.TagRepo
import com.example.application.repository.UserRepo
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

@Service
class EventService(private var eventRepo: EventRepo,
                   private var userRepo: UserRepo,
                   private var tagRepo: TagRepo) {

    fun getAllEvents(
            userId: UUID,
            perPage: Optional<Int>,
            page: Optional<Int>,
            simpleFilter: Optional<String>,
            tagFilter: Optional<Long>
    ): GetAllEventDTO {
        val page: Int = if (page.isEmpty) 0 else page.get()
        val perPage: Int = if (perPage.isEmpty) 8 else perPage.get()
        val simpleFilter: String = if (simpleFilter.isEmpty) "" else simpleFilter.get().lowercase()
        var eventInstances = eventRepo.findAllByUserId(userId).filter { event: Event ->
            event.eventName.lowercase().contains(simpleFilter) || event.eventDesc?.lowercase()?.contains(simpleFilter) ?: false
        }
        if (!tagFilter.isEmpty ) {
            val tagFilter = tagFilter.get()
            eventInstances = eventInstances.filter { event: Event ->
                event.tags!!.map { tag:Tag ->
                    tag.tagId
                }.contains(tagFilter)
            }
        }
        val eventInstancesAllAmount = eventInstances.size
        eventInstances = if (eventInstances.isNotEmpty())
            eventInstances.subList(
                    page * perPage,
                    if (eventInstances.size - 1 < (page + 1) * perPage)
                        eventInstances.size
                    else (page + 1) * perPage)
        else eventInstances
        val eventDTOList = listOf<EventDTO>().toMutableList()
        eventInstances.forEach { eventInstance ->
            val tagDTOCollection: MutableList<TagDTO> = mutableListOf()
            eventInstance.tags?.forEach {
                tagDTOCollection.add(TagDTO(
                        tagId = it.tagId,
                        tagColor = it.tagColor,
                        tagDesc = it.tagDescription,
                        tagName = it.tagName
                ))
            }
            eventDTOList.add(EventDTO(
                    eventId = eventInstance.eventId,
                    eventDesc = eventInstance.eventDesc,
                    eventDate = eventInstance.eventDate.let {if (it !== null) it.toString() else it},
                    eventName = eventInstance.eventName,
                    tags = tagDTOCollection,
                    eventCompletion = eventInstance.eventCompletion
            ))
        }
        return GetAllEventDTO(
                list = eventDTOList,
                currentPage = page,
                elementsPerPage = perPage,
                totalElements = eventInstancesAllAmount,
                totalPages = eventInstancesAllAmount / perPage
        )
    }

    fun findEventById(userId: UUID, eventId: Long): EventDTO {
        if (!eventRepo.existsById(eventId))
            throw IllegalStateException("Event with id $eventId does not exists")
        val eventInstance = eventRepo.findEventByUserIdAndEventId(userId, eventId).get()
        val tagDTOCollection: MutableList<TagDTO> = mutableListOf()

        eventInstance.tags?.forEach {
            tagDTOCollection.add(TagDTO(
                    tagId = it.tagId,
                    tagColor = it.tagColor,
                    tagDesc = it.tagDescription,
                    tagName = it.tagName
            ))
        }

        return EventDTO(
                eventId = eventInstance.eventId,
                eventDesc = eventInstance.eventDesc,
                eventDate = eventInstance.eventDate.let {if (it !== null) it.toString() else it},
                eventName = eventInstance.eventName,
                tags = tagDTOCollection,
                eventCompletion = eventInstance.eventCompletion
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
                eventCompletion = eventCreateDTO.eventCompletion
        )

        return eventRepo.save(eventInstance)
    }

    fun updateEvent(userId: UUID, eventId: Long, eventUpdateDTO: EventUpdateDTO): Event {
        val eventInstance = eventRepo.findById(eventId)
                .orElseThrow { java.lang.IllegalStateException("factor with id" + eventId + "does not exist") }

        if (userId.compareTo(eventInstance.user?.userId) != 0) {
            throw Exception("This user not allowed to use this event")
        }


        if (eventUpdateDTO.eventDate is LocalDateTime) {
            eventInstance.eventDate = eventUpdateDTO.eventDate!!
        }
        if (eventUpdateDTO.eventDesc is String) {
            eventInstance.eventDesc = eventUpdateDTO.eventDesc!!
        }
        if (eventUpdateDTO.eventName is String) {
            eventInstance.eventName = eventUpdateDTO.eventName!!
        }
        if (eventUpdateDTO.eventCompletion is Boolean) {
            eventInstance.eventCompletion = eventUpdateDTO.eventCompletion!!
        }
        if (eventUpdateDTO.tags is Collection<*>) {
            val newListTags: MutableList<Tag> = listOf<Tag>().toMutableList()
            eventUpdateDTO.tags?.forEach {
                val tag = tagRepo.findById(it)
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