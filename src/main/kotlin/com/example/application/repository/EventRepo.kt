package com.example.application.repository

import com.example.application.entity.Event
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.Optional
import java.util.UUID

@Repository
interface EventRepo: JpaRepository<Event, Long> {
    @Query("SELECT e from Event e WHERE e.user.userId = ?1")
    fun findAllByUserId(userId: UUID) : List<Event>
    @Query("SELECT e from Event e WHERE e.user.userId = ?1 and e.eventId = ?2")
    fun findEventByUserIdAndEventId(userId: UUID, eventId: Long) : Optional<Event>
    @Query("SELECT e from Event e WHERE e.eventName = ?1")
    fun findEventByName(eventName: String):Event

}