package com.example.application.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.annotations.SerializedName
import java.time.LocalDate
import java.time.LocalDateTime

data class EventCreateDTO(
        @JsonProperty("eventName")
        @SerializedName("eventName") var eventName: String,

        @JsonProperty("eventDesc")
        @SerializedName("eventDesc") var eventDesc: String? = null,

        @JsonProperty("eventDate")
        @SerializedName("eventDate") var eventDate: LocalDateTime? = null,

        @JsonProperty("eventCompletion")
        @SerializedName("eventCompletion") var eventCompletion: Boolean,

        @JsonProperty("tags")
        @SerializedName("tags") var tags: Collection<Long> ? = null
)