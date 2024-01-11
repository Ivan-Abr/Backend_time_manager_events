package com.example.application.dto
import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.annotations.SerializedName

data class TagUpdateDTO(
        @JsonProperty("tagName")
        @SerializedName("tagName") var tagName: String? = null,

        @JsonProperty("tagDesc")
        @SerializedName("tagDesc") var tagDesc: String? = null,

        @JsonProperty("tagColor")
        @SerializedName("tagColor") var tagColor: String? = null,
)