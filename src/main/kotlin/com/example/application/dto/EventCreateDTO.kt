package com.example.application.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.annotations.SerializedName
import java.time.LocalDate

open class EventCreateDTO(
        @JsonProperty("eventName")
        @SerializedName("eventName") var eventName: String? = null,

        @JsonProperty("eventDesc")
        @SerializedName("eventDesc") var eventDesc: String? = null,

        @JsonProperty("eventDate")
        @SerializedName("eventDate") var eventDate: LocalDate? = null,

        @JsonProperty("tags")
        @SerializedName("tags") var tags: Collection<Long>? = null,
)