package com.example.application.repository

import com.example.application.entity.Tag
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface TagRepo: JpaRepository<Tag,Long> {
    @Query("SELECT t FROM Tag t WHERE t.tagName = ?1")
    fun findTagByName(tagName: String): Tag

}