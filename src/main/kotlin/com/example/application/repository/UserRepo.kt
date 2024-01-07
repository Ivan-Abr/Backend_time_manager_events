package com.example.application.repository

import com.example.application.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface UserRepo: JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.name = ?1")
    fun findUserByName(userName: String):User
}