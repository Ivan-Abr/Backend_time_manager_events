package com.example.application.entity

import com.fasterxml.jackson.annotation.JsonIgnore
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
    var eventId: Long,

    @JsonProperty("event_name")
    @Column(name = "event_name")
    var eventName: String,

    @JsonProperty("event_desc")
    @Column(name = "event_desc")
    var eventDesc: String,

    @JsonProperty("event_date")
    @Column(name = "event_date")
    var eventDate: LocalDate,

    @ManyToMany
    @JoinTable(
    name = "tag_event",
    joinColumns = [JoinColumn(name = "event_id")],
    inverseJoinColumns = [JoinColumn(name = "tag_id")])
    var tags: Set<Tag>? = HashSet(),

    @ManyToOne
    @JoinColumn(name = "user_id")
    var user:User?,


    @OneToMany(mappedBy="event")
    @JsonIgnore
    var notes: Set<Notification>?

)
