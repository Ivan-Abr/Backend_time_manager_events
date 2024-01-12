package com.example.application.controller

import USER_REQUEST_KEY
import com.example.application.dto.EventCreateDTO
import com.example.application.dto.EventDTO
import com.example.application.dto.EventUpdateDTO
import com.example.application.dto.TagUpdateDTO
import com.example.application.entity.Event
import com.example.application.entity.Tag
import com.example.application.service.EventService
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
@RequestMapping("timemanager/event")
class EventController(private var eventService: EventService) {


    @Operation(summary = "Вывод списка всех событий")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "События найдены", content = [Content(
            mediaType = "application/json",
            array = ArraySchema(schema = Schema(implementation = Tag::class))
        )]),
        ApiResponse(responseCode = "404", description = "События не найдены", content = [Content()]),)
    @GetMapping()
    fun getAllEvents(
        @Parameter(description = "id пользователя")
        @RequestAttribute(USER_REQUEST_KEY) userId: UUID): List<EventDTO> {
        return eventService.getAllEvents(userId)
    }

    @Operation(summary = "Вывод события по его id")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Событие найдено", content = [Content(
            mediaType = "application/json",
            array = ArraySchema(schema = Schema(implementation = Tag::class)))]),
        ApiResponse(responseCode = "400", description = "Введен неверный id", content = [Content()]),
        ApiResponse(responseCode = "500", description = "Событие не найдено", content = [Content()]))
    @GetMapping(path = ["{eventId}"])
    fun getEventById(
        @Parameter(description = "id пользователя")
        @RequestAttribute(USER_REQUEST_KEY) userId: UUID,

        @Parameter(description = "id события")
        @PathVariable("eventId") eventId: Long): EventDTO {
        return eventService.findEventById(userId, eventId)
    }

    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Событие найдено", content = [Content(
            mediaType = "application/json",
            array = ArraySchema(schema = Schema(implementation = Tag::class)))]),
        ApiResponse(responseCode = "400", description = "Введены неверные данные", content = [Content()]),
        ApiResponse(responseCode = "500", description = "Событие не найдено", content = [Content()]))
    @Operation(summary = "Создание нового события")
    @PostMapping
    fun registerEvent(
        @Parameter(description = "id события")
        @RequestAttribute(USER_REQUEST_KEY) userId: UUID,

        @Parameter(description = "dto  для добавления ",
            schema = Schema(implementation = TagUpdateDTO::class))
        @RequestBody eventCreateDTO: EventCreateDTO): Event {
        return eventService.createEvent(userId, eventCreateDTO)
    }



    @Operation(summary = "Обновление полей существующего события")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Событие найдено", content = [Content(
            mediaType = "application/json",
            array = ArraySchema(schema = Schema(implementation = Tag::class)))]),
        ApiResponse(responseCode = "400", description = "Введены неверные данные", content = [Content()]),
        ApiResponse(responseCode = "500", description = "Событие не найдено", content = [Content()]))
    @PatchMapping(path = ["{eventId}"])
    fun updateEvent(
        @Parameter(description = "id события")
        @RequestAttribute(USER_REQUEST_KEY) userId: UUID,

        @Parameter(description = "id события")
        @PathVariable("eventId") eventId: Long,

        @Parameter(description = "dto  для изменения ")
        @RequestBody eventUpdateDTO: EventUpdateDTO): Event {
        return eventService.updateEvent(userId, eventId, eventUpdateDTO)
    }


    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Событие найдено", content = [Content()]),
        ApiResponse(responseCode = "500", description = "Событие не найдено", content = [Content()]))
    @Operation(summary = "Удаление существующего события")
    @DeleteMapping(path = ["{eventId}"])
    fun deleteEventById(
        @Parameter(description = "id события")
        @RequestAttribute(USER_REQUEST_KEY) userId: UUID,

        @Parameter(description = "id события")
        @PathVariable("eventId") eventId: Long) {
        return eventService.deleteEventById(userId, eventId)
    }
}