package com.example.application.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.annotations.SerializedName

data class GetAllTagDTO (
        @JsonProperty("list")
        @SerializedName("list")
        var list: List<TagDTO> = listOf(),

        @JsonProperty("totalElements")
        @SerializedName("totalElements")
        var totalElements: Int,

        @JsonProperty("totalPages")
        @SerializedName("totalPages")
        var totalPages: Int,

        @JsonProperty("currentPage")
        @SerializedName("currentPage")
        var currentPage: Int,

        @JsonProperty("elementsPerPage")
        @SerializedName("elementsPerPage")
        var elementsPerPage: Int,
)