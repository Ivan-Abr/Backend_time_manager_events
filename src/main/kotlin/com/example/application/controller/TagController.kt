package com.example.application.controller

import USER_REQUEST_KEY
import com.example.application.dto.*
import com.example.application.entity.Event
import com.example.application.entity.Tag
import com.example.application.service.TagService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("timemanager/tag")
class TagController(private var tagService: TagService) {

    @GetMapping()
    fun getAllTags(
            @RequestAttribute(USER_REQUEST_KEY) userId: UUID,
            @RequestParam("perPage") perPage: Optional<Int>,
            @RequestParam("page") page: Optional<Int>,
            @RequestParam("simpleFilter") simpleFilter: Optional<String>
    ): GetAllTagDTO {
        return tagService.getAllTags(userId,perPage, page, simpleFilter)
    }

    @GetMapping(path = ["{tagId}"])
    fun getTagById(@PathVariable("tagId") tagId: Long): TagDTO {
        return tagService.findTagById(tagId)
    }

    @PostMapping
    fun createTag(@RequestAttribute(USER_REQUEST_KEY) userId: UUID, @RequestBody tagCreateDTO: TagCreateDTO): Tag {
        return tagService.createTag(userId, tagCreateDTO)
    }

    @PatchMapping(path = ["{tagId}"])
    fun updateTag(@RequestAttribute(USER_REQUEST_KEY) userId: UUID, @PathVariable("tagId") tagId: Long, @RequestBody tagUpdateDTO: TagUpdateDTO): Tag {
        return tagService.updateTag(userId, tagId, tagUpdateDTO)
    }

    @DeleteMapping(path = ["{tagId}"])
    fun deleteTagById(@RequestAttribute(USER_REQUEST_KEY) userId: UUID, @PathVariable("tagId") tagId: Long) {
        return tagService.deleteTagById(userId, tagId)
    }
}