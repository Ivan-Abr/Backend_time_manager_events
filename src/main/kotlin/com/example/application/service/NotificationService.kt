package com.example.application.service

import com.example.application.entity.Event
import com.example.application.entity.Notification
import com.example.application.repository.NotificationRepo
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.util.*

@Service
class NotificationService(private var notificationRepo: NotificationRepo) {

    fun getAllNotifications():List<Notification>{
        return notificationRepo.findAll()
    }

    fun findNotificationById(notificationId: Long): Optional<Notification> {
        if (!notificationRepo.existsById(notificationId))
            throw IllegalStateException("Notification with id $notificationId does not exists")
        return notificationRepo.findById(notificationId)
    }

    fun createNewNotification(notification: Notification):Notification{
        return notificationRepo.save(notification)
    }



    fun updateNotificationTrigger(notificationId: Long, trigger: LocalDate): Notification?{
        val notification = notificationRepo.findById(notificationId)
            .orElseThrow { java.lang.IllegalStateException("factor with id" + notificationId + "does not exist") }
        notification.triggerDateTime = trigger
        return notificationRepo.save(notification)
    }

    fun deleteNotificationById(notificationId: Long){
        if (!notificationRepo.existsById(notificationId))
            throw IllegalStateException("Notification with id $notificationId does not exists")
        return notificationRepo.deleteById(notificationId)
    }

}