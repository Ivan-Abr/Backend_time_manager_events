package com.example.application.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

@Entity
@Table(name="usr")
data class User(
    @Id
    @JsonProperty("usr_id")
    @Column(name="user_id")
    var userId: Long,

    @JsonProperty("password")
    @Column(name="password")
    var password: String,

    @JsonProperty("name")
    @Column(name="name")
    var name: String,

    @OneToMany(mappedBy="user")
    @JsonIgnore
    var events: Set<Event?>?



)
