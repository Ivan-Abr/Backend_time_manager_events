package com.example.application.service

import com.example.application.entity.Event
import com.example.application.entity.User
import com.example.application.repository.EventRepo
import com.example.application.repository.UserRepo
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService(private var userRepo: UserRepo,
    private var eventRepo: EventRepo) {

    fun getAllUsers():List<User>{
        return userRepo.findAll()
    }

    fun findUserById(userId: Long): Optional<User> {
        if (!userRepo.existsById(userId))
            throw IllegalStateException("User with id $userId does not exists")
        return userRepo.findById(userId)
    }

    fun createNewUser(user: User):User?{
        if (userRepo.findUserByName(user.name)!=null) {
            throw IllegalStateException("User with name ${user.name} already exists")
        }
        return userRepo.save(user)
    }

    fun updateUserName(userId: Long, userName:String):User?{
        val user = userRepo.findById(userId)
            .orElseThrow { java.lang.IllegalStateException("factor with id" + userId + "does not exist") }

        if (userName != null && userName.isNotEmpty() && user.name!= userName)
            user.name = userName
        return userRepo.save(user)
    }

    fun assignEventsToUser(userId: Long, eventId: Long): User?{
        val user = userRepo.findById(userId).get()
        val event = eventRepo.findById(eventId).get()

        user.events = (user.events as MutableSet<Event>).apply {
            add(event)
        }
        return  userRepo.save(user)

    }

    fun deleteUserById(userId: Long){
        if (!userRepo.existsById(userId))
            throw IllegalStateException("User with id $userId does not exists")
        return userRepo.deleteById(userId)
    }

}