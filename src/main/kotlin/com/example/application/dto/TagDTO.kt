package com.example.application.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.annotations.SerializedName

data class TagDTO (
        @JsonProperty("tagId")
        @SerializedName("tagId") var tagId: Long,

        @JsonProperty("tagName")
        @SerializedName("tagName") var tagName: String,

        @JsonProperty("tagDesc")
        @SerializedName("tagDesc") var tagDesc: String? = null,

        @JsonProperty("tagColor")
        @SerializedName("tagColor") var tagColor: String,
)