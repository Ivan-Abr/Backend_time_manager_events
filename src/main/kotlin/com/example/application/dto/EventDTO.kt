package com.example.application.dto

import com.example.application.entity.Tag
import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.annotations.SerializedName
import java.time.LocalDate

data class EventDTO(
        @JsonProperty("eventId")
        @SerializedName("eventId") var eventId: Long,

        @JsonProperty("eventName")
        @SerializedName("eventName") var eventName: String,

        @JsonProperty("eventDesc")
        @SerializedName("eventDesc") var eventDesc: String? = null,

        @JsonProperty("eventDate")
        @SerializedName("eventDate") var eventDate: String? = null,

        @JsonProperty("eventCompletion")
        @SerializedName("eventCompletion") var eventCompletion: Boolean,

        @JsonProperty("tags")
        @SerializedName("tags") var tags: List<TagDTO>? = null
)