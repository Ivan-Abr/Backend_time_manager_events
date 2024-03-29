package com.example.application.controller

import USER_REQUEST_KEY
import com.example.application.dto.GetAllTagDTO
import com.example.application.dto.TagCreateDTO
import com.example.application.dto.TagDTO
import com.example.application.dto.TagUpdateDTO
import com.example.application.entity.Tag
import com.example.application.service.TagService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.web.bind.annotation.*
import java.util.*


@RestController
@RequestMapping("timemanager/tag")
//@SecurityRequirement(name = "bearerAuth")

class TagController(private var tagService: TagService) {


    @Operation(summary = "Вывод списка всех тегов")
    @ApiResponses(
            ApiResponse(responseCode = "200", description = "Теги найдены", content = [Content(
                    mediaType = "application/json",
                    array = ArraySchema(schema = Schema(implementation = Tag::class)))]),
            ApiResponse(responseCode = "401", description = "Токен не валидный", content = [Content()]),
            ApiResponse(responseCode = "404", description = "Теги не найдены", content = [Content()]),
    )
    @GetMapping()
    fun getAllTags(
            @RequestAttribute(USER_REQUEST_KEY) userId: UUID,
            @RequestParam("perPage") perPage: Optional<Int>,
            @RequestParam("page") page: Optional<Int>,
            @RequestParam("simpleFilter") simpleFilter: Optional<String>
    ): GetAllTagDTO {
        return tagService.getAllTags(userId, perPage, page, simpleFilter)
    }

    @Operation(summary = "Вывод тега по его id")
    @ApiResponses(
            ApiResponse(responseCode = "200", description = "Тег найден", content = [Content(
                    mediaType = "application/json",
                    array = ArraySchema(schema = Schema(implementation = GetAllTagDTO::class)))]),
            ApiResponse(responseCode = "400", description = "Введен неверный id", content = [Content()]),
            ApiResponse(responseCode = "401", description = "Токен не валидный", content = [Content()]),
            ApiResponse(responseCode = "500", description = "Тега не существует", content = [Content()]))
    @GetMapping(path = ["{tagId}"])
    fun getTagById(@PathVariable("tagId") tagId: Long): TagDTO {
        return tagService.findTagById(tagId)
    }

    @Operation(summary = "Создание нового тега")
    @ApiResponses(
            ApiResponse(responseCode = "200", description = "Тег создан", content = [Content(
                    mediaType = "application/json",
                    array = ArraySchema(schema = Schema(implementation = TagDTO::class)))]),
            ApiResponse(responseCode = "400", description = "Введены неверные данные", content = [Content()]),
            ApiResponse(responseCode = "401", description = "Токен не валидный", content = [Content()]),
    )
    @PostMapping
    fun createTag(@Parameter(description = "id пользователя ")
                  @RequestAttribute(USER_REQUEST_KEY) userId: UUID,

                  @Parameter(description = "dto  для добавления ",
                          schema = Schema(implementation = Tag::class))
                  @RequestBody tagCreateDTO: TagCreateDTO): Tag {
        return tagService.createTag(userId, tagCreateDTO)
    }

    @Operation(summary = "Обновление полей существующего тега")
    @ApiResponses(
            ApiResponse(responseCode = "200", description = "Тег обновлен", content = [Content(
                    mediaType = "application/json",
                    array = ArraySchema(schema = Schema(implementation = Tag::class)))]),
            ApiResponse(responseCode = "400", description = "Введены неверные данные", content = [Content()]),
            ApiResponse(responseCode = "401", description = "Токен не валидный", content = [Content()]),
            ApiResponse(responseCode = "500", description = "Тега не существует", content = [Content()]))
    @PatchMapping(path = ["{tagId}"])
    fun updateTag(
            @Parameter(description = "id пользователя")
            @RequestAttribute(USER_REQUEST_KEY) userId: UUID,

            @Parameter(description = "id тега")
            @PathVariable("tagId") tagId: Long,

            @Parameter(description = "dto  для изменения ",
                    schema = Schema(implementation = TagUpdateDTO::class))
            @RequestBody tagUpdateDTO: TagUpdateDTO): Tag {
        return tagService.updateTag(userId, tagId, tagUpdateDTO)
    }

    @Operation(summary = "Удаление существующего тега")
    @ApiResponses(
            ApiResponse(responseCode = "200", description = "Тег удален", content = [Content()]),
            ApiResponse(responseCode = "401", description = "Токен не валидный", content = [Content()]),
            ApiResponse(responseCode = "500", description = "Тега не существует", content = [Content()]))
    @DeleteMapping(path = ["{tagId}"])
    fun deleteTagById(
            @Parameter(description = "id пользователя")
            @RequestAttribute(USER_REQUEST_KEY) userId: UUID,

            @Parameter(description = "id тега")
            @PathVariable("tagId") tagId: Long) {
        return tagService.deleteTagById(userId, tagId)
    }
}