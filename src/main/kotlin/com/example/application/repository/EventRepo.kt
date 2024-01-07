package com.example.application.repository

import com.example.application.entity.Event
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface EventRepo: JpaRepository<Event, Long> {
    @Query("SELECT e from Event e WHERE e.eventName = ?1")
    fun findEventByName(eventName: String):Event

}