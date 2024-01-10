package com.example.application.controller

import com.example.application.entity.Tag
import com.example.application.service.TagService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("timemanager/tag")
class TagController(private var tagService: TagService) {

    @GetMapping()
    fun getAllTags(): List<Tag> {
        return tagService.getAllTags()
    }

    @GetMapping(path = ["{tagId}"])
    fun getTagById(@PathVariable("tagId") tagId:Long): Optional<Tag> {
        return tagService.findTagById(tagId)
    }

    @PostMapping
    fun registerNewTag(@RequestBody tag: Tag){
        tagService.createNewTag(tag)
    }

    @PutMapping(path = ["{tagId}/name"])
    fun changeTagName(@PathVariable("tagId") tagId: Long,
                      @RequestParam(required = false) tagName: String){
        tagService.updateTagName(tagId, tagName)
    }


    @PutMapping(path = ["{tagId}/desc"])
    fun changeTagDesc(@PathVariable("tagId") tagId: Long,
                      @RequestParam(required = false) tagDesc: String){
        tagService.updateTagDesc(tagId, tagDesc)
    }

    @DeleteMapping(path = ["{tagId}"])
    fun deleteTagById(@PathVariable("tagId") tagId:Long){
        return tagService.deleteTagById(tagId)
    }
}