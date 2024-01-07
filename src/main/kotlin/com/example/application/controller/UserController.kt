package com.example.application.controller

import com.example.application.entity.Event
import com.example.application.entity.User
import com.example.application.service.UserService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import java.util.*

@Controller
@RequestMapping("user")
class UserController(private var userService: UserService) {

    @GetMapping()
    fun getAllUsers(): List<User> {
        return userService.getAllUsers()
    }

    @GetMapping(path = ["{userId}"])
    fun getUserById(@PathVariable("userId") userId:Long): Optional<User> {
        return userService.findUserById(userId)
    }

    @PostMapping
    fun registerNewUser(@RequestBody user: User){
        userService.createNewUser(user)
    }

    @PutMapping(path = ["{userId}/name"])
    fun changeUserName(@PathVariable("userId") userId: Long,
                        @RequestParam(required = false) userName: String){
        userService.updateUserName(userId, userName)
    }

    @PutMapping(path = ["{userId}/event/{eventId}"])
    fun assignEventToUser(@PathVariable("userId") userId: Long,
                         @PathVariable("eventId") eventId: Long): User?{
        return userService.assignEventsToUser(userId, eventId)
    }

    @DeleteMapping(path = ["{userId}"])
    fun deleteUserById(@PathVariable("userId") userId:Long){
        return userService.deleteUserById(userId)
    }
}