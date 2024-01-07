package com.example.application.repository

import com.example.application.entity.Notification
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface NotificationRepo: JpaRepository<Notification, Long> {

}