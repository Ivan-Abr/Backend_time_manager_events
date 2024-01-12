package com.example.application.entity

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.persistence.*
import java.time.LocalDate


@Entity
@Table(name = "event")
data class Event(
        @Id
        @JsonProperty("event_id")
        @Column(name = "event_id")
        @GeneratedValue
        var eventId: Long = 0,

        @JsonProperty("event_name")
        @Column(name = "event_name")
        var eventName: String,

        @JsonProperty("event_desc")
        @Column(name = "event_desc", nullable = true)
        var eventDesc: String? = null,

        @JsonProperty("event_date")
        @Column(name = "event_date" , nullable = true)
        var eventDate: LocalDate? = null,

        @JsonProperty("event_completion")
        @Column(name = "event_completion")
        var eventCompletion: Boolean,

        @ManyToMany(fetch = FetchType.LAZY)
        @JoinTable(
                name = "tag_event",
                joinColumns = [JoinColumn(name = "event_id", referencedColumnName = "event_id")],
                inverseJoinColumns = [JoinColumn(name = "tag_id", referencedColumnName = "tag_id")])
        var tags: List<Tag>? = mutableListOf(),

        @ManyToOne
        @JoinColumn(name = "user_id")
        var user: User?,
)
