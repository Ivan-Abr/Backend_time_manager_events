package com.example.application.dto
import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.annotations.SerializedName

data class TagCreateDTO(
        @JsonProperty("tagName")
        @SerializedName("tagName") var tagName: String,

        @JsonProperty("tagDesc")
        @SerializedName("tagDesc") var tagDesc: String,

        @JsonProperty("tagColor")
        @SerializedName("tagColor") var tagColor: String,
)