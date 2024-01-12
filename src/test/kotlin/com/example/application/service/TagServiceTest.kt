package com.example.application.service

import com.example.application.dto.TagCreateDTO
import com.example.application.dto.TagUpdateDTO
import com.example.application.entity.Event
import com.example.application.entity.Tag
import com.example.application.entity.User
import com.example.application.repository.TagRepo
import com.example.application.repository.UserRepo
import jakarta.transaction.Transactional
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.argThat
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDate
import java.util.*

@SpringBootTest
class TagServiceTest {


    @Mock
    private lateinit var tagRepo: TagRepo

    @Mock
    private lateinit var userRepo: UserRepo

    @InjectMocks
    private lateinit var tagService: TagService


    private lateinit var tagCreateDTO: TagCreateDTO


    private lateinit var tag: Tag

    @BeforeEach
    fun setup() {
        tag = Tag(
            1L,
            "",
            "",
            "",
            null,
            User(
                UUID.randomUUID(),
                null
            )
        )
    }


    @Test
    @Transactional
    fun getAllTags() {
        var userId = tag.user!!.userId
        Mockito.`when`(tagRepo.findAllByUserId(userId)).thenReturn(listOf(tag!!))
        assertNotNull(tagService.getAllTags(userId))
    }

    @Test
    @Transactional
    fun findTagById() {
        var tagId: Long = 1L
        Mockito.`when`(tagRepo.findById(any())).thenReturn(Optional.ofNullable(tag))
        Mockito.`when`(tagRepo.existsById(any())).thenReturn(true)
        val tag2 = tagService.findTagById(tagId)
        assertNotNull(tag2)
    }

    @Test
    @Transactional
    fun createTag() {
        val userId = UUID.randomUUID()
        val tagCreateDTO = TagCreateDTO("TagName", "TagDescription", "TagColor")
        val userInstance = User(userId, null)
        Mockito.`when`(userRepo.findById(userId)).thenReturn(Optional.of(userInstance))
        val savedTagInstance =
            Tag(1L, tagCreateDTO.tagName, tagCreateDTO.tagDesc, tagCreateDTO.tagColor, null, userInstance)
        Mockito.`when`(tagRepo.save(any<Tag>())).thenReturn(savedTagInstance)
        val createTag = tagService.createTag(userId, tagCreateDTO)
        assertEquals(savedTagInstance, createTag)

    }

    @Test
    @Transactional
    fun updateTag() {
        val userId: UUID = UUID.randomUUID()
        val tagId = 1L
        val tagUpdateDTO = TagUpdateDTO("NewTagName", "NewTagDesc", "NewTagColor")
        val userInstance = User(userId, null)
        val tagInstance = Tag(tagId, "tagName", "tagDesc", "tagColor", null, userInstance)

        Mockito.`when`(tagRepo.findById(tagId)).thenReturn(Optional.of(tagInstance))
        val newTagInstance = Tag(
            tagInstance.tagId,
            tagUpdateDTO.tagName!!,
            tagUpdateDTO.tagDesc!!,
            tagUpdateDTO.tagColor!!,
            null,
            userInstance
        )
        Mockito.`when`(tagRepo.save(any())).thenReturn(newTagInstance)
        val updatedTag = tagService.updateTag(userId, tagId, tagUpdateDTO)
        assertEquals(newTagInstance, updatedTag)

    }

    @Test
    @Transactional
    fun updateTag_ThrowsException_WhenUserNotAllowed() {
        val userId = UUID.randomUUID()
        val tagId = 1L
        val tagUpdateDTO = TagUpdateDTO("UpdatedTagName", "UpdatedTagDescription", "UpdatedTagColor")
        val userInstance = User(UUID.randomUUID(), null)
        val tagInstance = Tag(tagId, "tagName", "tagDesc", "tagColor", null, userInstance)

        Mockito.`when`(tagRepo.findById(tagId)).thenReturn(Optional.of(tagInstance))
        assertThrows<Exception> { tagService.updateTag(userId, tagId, tagUpdateDTO) }

    }


    @Test
    @Transactional
    fun updateTag_ThrowsException_WhenTagNotFound() {
        val userId = UUID.randomUUID()
        val tagId = 1L
        val tagUpdateDTO = TagUpdateDTO("UpdatedTagName", "UpdatedTagDescription", "UpdatedTagColor")
        Mockito.`when`(tagRepo.findById(tagId)).thenReturn(Optional.empty())
        assertThrows<IllegalStateException> { tagService.updateTag(userId, tagId, tagUpdateDTO) }
    }


    @Test
    fun deleteTagById() {
        val userId = UUID.randomUUID()
        val tagId = 1L
        val userInstance = User(userId, null)
        val tagInstance = Tag(tagId, "tagName", "tagDesc", "tagColor", null, userInstance)
        Mockito.`when`(tagRepo.findById(tagId)).thenReturn(Optional.of(tagInstance))
        tagService.deleteTagById(userId, tagId)
        verify(tagRepo).findById(tagId)
        verify(tagRepo).deleteById(tagId)
    }

    @Test
    fun deleteTagById_ThrowsException_WhenTagNotFound(){
        val userId = UUID.randomUUID()
        val tagId = 1L
        Mockito.`when`(tagRepo.findById(tagId)).thenReturn(Optional.empty())
        assertThrows<IllegalStateException> { tagService.deleteTagById(userId, tagId) }
    }

    @Test
    fun deleteTagById_ThrowsException_ThrowsException_WhenUserNotAllowed(){
        val userId = UUID.randomUUID()
        val tagId = 1L
        val userInstance = User(UUID.randomUUID(), null)
        val tagInstance = Tag(tagId, "tagName", "tagDesc", "tagColor", null, userInstance)
        Mockito.`when`(tagRepo.findById(tagId)).thenReturn(Optional.of(tagInstance))
        assertThrows<Exception> { tagService.deleteTagById(userId, tagId) }
    }
}



