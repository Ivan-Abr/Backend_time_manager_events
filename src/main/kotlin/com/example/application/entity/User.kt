package com.example.application.entity
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "usr")
data class User(
        @Id
        @JsonProperty("usr_id")
        @Column(name = "user_id")
        var userId: UUID,

        @OneToMany(mappedBy = "user")
        @JsonIgnore
        var events: Set<Event?>?
)
