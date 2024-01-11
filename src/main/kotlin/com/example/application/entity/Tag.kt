package com.example.application.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.persistence.*

@Entity
@Table(name = "tag")
data class Tag(

    @Id
    @JsonProperty("tag_id")
    @Column(name = "tag_id")
    @GeneratedValue
    var tagId: Long = 0,

    @JsonProperty("tag_name")
    @Column(name = "tag_name")
    var tagName: String,

    @JsonProperty("tag_desc")
    @Column(name = "tag_desc")
    var tagDescription: String,

    @JsonProperty("tag_color")
    @Column(name = "tag_color")
    var tagColor: String,

    @JsonIgnoreProperties
    @ManyToMany(mappedBy = "tags",fetch = FetchType.LAZY)
    private var events: List<Event>? = mutableListOf(),

    @ManyToOne
    @JoinColumn(name = "user_id")
    var user:User?,
)
{
    override fun toString(): String {
        return "Tag(tagId=$tagId, tagName='$tagName', tagDescription='$tagDescription', tagColor='$tagColor')"
    }
}