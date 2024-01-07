package com.example.application.controller

import com.example.application.entity.Notification
import com.example.application.service.NotificationService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import java.time.LocalDate
import java.util.*

@Controller
@RequestMapping("notification")
class NotificationController(private var notificationService: NotificationService) {

    @GetMapping()
    fun getAllNotifications(): List<Notification> {
        return notificationService.getAllNotifications()
    }

    @GetMapping(path = ["{notificationId}"])
    fun getNotificationById(@PathVariable("notificationId") notificationId:Long): Optional<Notification> {
        return notificationService.findNotificationById(notificationId)
    }

    @PostMapping
    fun registerNewNotification(@RequestBody notification: Notification){
        notificationService.createNewNotification(notification)
    }

    @PutMapping(path = ["{notificationId}/name"])
    fun changeNotificationName(@PathVariable("notificationId") notificationId: Long,
                       @RequestParam(required = false) trigger: LocalDate){
        notificationService.updateNotificationTrigger(notificationId, trigger)
    }



    @DeleteMapping(path = ["{notificationId}"])
    fun deleteNotificationById(@PathVariable("notificationId") notificationId:Long){
        return notificationService.deleteNotificationById(notificationId)
    }
}