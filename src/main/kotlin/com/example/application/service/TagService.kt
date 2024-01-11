package com.example.application.service

import com.example.application.dto.TagCreateDTO
import com.example.application.dto.TagUpdateDTO
import com.example.application.entity.Tag
import com.example.application.repository.TagRepo
import com.example.application.repository.UserRepo
import org.springframework.stereotype.Service
import java.util.*

@Service
class TagService(private var tagRepo: TagRepo, private val userRepo: UserRepo) {

    fun getAllTags(userId: UUID): List<Tag> {
        return tagRepo.findAllByUserId(userId)
    }

    fun findTagById(tagId: Long): Optional<Tag> {
        if (!tagRepo.existsById(tagId))
            throw IllegalStateException("Tag with id $tagId does not exists")
        return tagRepo.findById(tagId)
    }

    fun createTag(userId: UUID, tagCreateDTO: TagCreateDTO): Tag {
        val userInstance = userRepo.findById(userId).get()
        val tagInstance = Tag(
                user = userInstance,
                tagColor = tagCreateDTO.tagColor,
                tagDescription = tagCreateDTO.tagDesc,
                tagName = tagCreateDTO.tagName
        )
        return tagRepo.save(tagInstance)
    }

    fun updateTag(userId: UUID, tagId: Long, tagUpdateDTO: TagUpdateDTO): Tag {
        val tagInstance = tagRepo.findById(tagId)
                .orElseThrow { java.lang.IllegalStateException("factor with id" + tagId + "does not exist") }

        if (userId.compareTo(tagInstance.user?.userId) != 0) {
            throw Exception("This user not allowed to use this event")
        }

        if (tagUpdateDTO.tagDesc is String) {
            tagInstance.tagDescription = tagUpdateDTO.tagDesc!!
        }
        if (tagUpdateDTO.tagName is String) {
            tagInstance.tagName = tagUpdateDTO.tagName!!
        }
        if (tagUpdateDTO.tagColor is String) {
            tagInstance.tagColor = tagUpdateDTO.tagColor!!
        }

        return tagRepo.save(tagInstance)
    }

    fun deleteTagById(userId: UUID, tagId: Long) {
        val tagInstance = tagRepo.findById(tagId)
                .orElseThrow { java.lang.IllegalStateException("factor with id" + tagId + "does not exist") }

        if (userId.compareTo(tagInstance.user?.userId) != 0) {
            throw Exception("This user not allowed to use this event")
        }

        return tagRepo.deleteById(tagId)
    }

}