package com.example.application.service
import com.example.application.dto.GetAllTagDTO
import com.example.application.dto.TagCreateDTO
import com.example.application.dto.TagDTO
import com.example.application.dto.TagUpdateDTO
import com.example.application.entity.Tag
import com.example.application.repository.TagRepo
import com.example.application.repository.UserRepo
import org.springframework.stereotype.Service
import java.util.*

@Service
class TagService(private var tagRepo: TagRepo, private val userRepo: UserRepo) {

    fun getAllTags(
            userId: UUID,
            perPage: Optional<Int>,
            page: Optional<Int>,
            simpleFilter: Optional<String>,
    ): GetAllTagDTO {
        val page: Int = if (page.isEmpty) 0 else page.get()
        val perPage: Int = if (perPage.isEmpty) 8 else perPage.get()
        val simpleFilter: String = if (simpleFilter.isEmpty) "" else simpleFilter.get()

        var tagInstances = tagRepo.findAllByUserId(userId).filter { tag: Tag ->
            tag.tagName.contains(simpleFilter) || tag.tagDescription.contains(simpleFilter)
        }

        val tagInstancesAllAmount = tagInstances.size
        tagInstances = if (tagInstances.isNotEmpty())
            tagInstances.subList(
                    page * perPage,
                    if (tagInstances.size - 1 < (page + 1) * perPage)
                        tagInstances.size
                    else (page + 1) * perPage)
        else tagInstances
        val tagDTOList = listOf<TagDTO>().toMutableList()
        tagInstances.forEach { tagInstance ->
            tagDTOList.add(TagDTO(
                    tagId = tagInstance.tagId,
                    tagName = tagInstance.tagName,
                    tagColor = tagInstance.tagColor,
                    tagDesc = tagInstance.tagDescription
            ))
        }
        return GetAllTagDTO(
                list = tagDTOList,
                currentPage = page,
                elementsPerPage = perPage,
                totalElements = tagInstancesAllAmount,
                totalPages = tagInstancesAllAmount / perPage
        )
    }

    fun findTagById(tagId: Long): TagDTO {
        if (!tagRepo.existsById(tagId))
            throw IllegalStateException("Tag with id $tagId does not exists")
        val tagInstance = tagRepo.findById(tagId).get()

        return TagDTO(
                tagId = tagInstance.tagId,
                tagName = tagInstance.tagName,
                tagColor = tagInstance.tagColor,
                tagDesc = tagInstance.tagDescription
        )
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