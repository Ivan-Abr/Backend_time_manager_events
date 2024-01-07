package com.example.application.service

import com.example.application.entity.Event
import com.example.application.entity.Tag
import com.example.application.repository.TagRepo
import org.springframework.stereotype.Service
import java.util.*

@Service
class TagService(private var tagRepo: TagRepo) {

    fun getAllTags():List<Tag>{
        return tagRepo.findAll()
    }

    fun findTagById(tagId: Long): Optional<Tag> {
        if (!tagRepo.existsById(tagId))
            throw IllegalStateException("Tag with id $tagId does not exists")
        return tagRepo.findById(tagId)
    }

    fun createNewTag(tag: Tag):Tag{
        if (tagRepo.findTagByName(tag.tagName)!=null) {
            throw IllegalStateException("Tag with name ${tag.tagName} already exists")
        }
        return tagRepo.save(tag)
    }

    fun updateTagName(tagId: Long, tagName:String):Tag?{
        val tag = tagRepo.findById(tagId)
            .orElseThrow { java.lang.IllegalStateException("factor with id" + tagId + "does not exist") }

        if (tagName != null && tagName.isNotEmpty() && tag.tagName!= tagName)
            tag.tagName = tagName
        return tagRepo.save(tag)
    }

    fun updateTagDesc(tagId: Long, tagDesc:String): Tag?{
        val tag = tagRepo.findById(tagId)
            .orElseThrow { java.lang.IllegalStateException("factor with id" + tagId + "does not exist") }

        if (tagDesc != null && tagDesc.isNotEmpty() && tag.tagDescription!= tagDesc)
            tag.tagDescription = tagDesc
        return tagRepo.save(tag)
    }

    fun deleteTagById(tagId: Long){
        if (!tagRepo.existsById(tagId))
            throw IllegalStateException("Tag with id $tagId does not exists")
        return tagRepo.deleteById(tagId)
    }

}