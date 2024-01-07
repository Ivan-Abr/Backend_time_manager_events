package com.example.application.entity

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "notification")
data class Notification(
    @Id
    @JsonProperty("not_id")
    @Column(name = "not_id")
    var notId: Long,


    @JsonProperty("trigger_date_time")
    @Column(name = "trigger_date_time")
    var triggerDateTime: LocalDate,


    @ManyToOne
    @JoinColumn(name = "event_id")
    var event: Event,
){

}
