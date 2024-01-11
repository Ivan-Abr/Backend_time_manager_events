package com.example.application.repository

import com.example.application.entity.Event
import com.example.application.entity.Tag
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface TagRepo: JpaRepository<Tag,Long> {
    @Query("SELECT t from Tag t WHERE t.user.userId = ?1")
    fun findAllByUserId(userId: UUID) : List<Tag>
}