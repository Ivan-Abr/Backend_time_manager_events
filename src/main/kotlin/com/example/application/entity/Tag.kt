package com.example.application.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.persistence.*

@Entity
@Table(name = "tag")
data class Tag(

    @Id
    @JsonProperty("tag_id")
    @Column(name = "tag_id")
    @GeneratedValue
    var tagId: Long,

    @JsonProperty("tag_name")
    @Column(name = "tag_name")
    var tagName: String,

    @JsonProperty("tag_desc")
    @Column(name = "tag_desc")
    var tagDescription: String,

    @JsonProperty("tag_color")
    @Column(name = "tag_color")
    var tagColor: String,

    @JsonIgnore
    @ManyToMany(mappedBy = "tags")
    private var events: Set<Event>? = HashSet(),
)
{
    override fun toString(): String {
        return "Tag(tagId=$tagId, tagName='$tagName', tagDescription='$tagDescription', tagColor='$tagColor')"
    }
}