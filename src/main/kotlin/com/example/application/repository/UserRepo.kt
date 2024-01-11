package com.example.application.repository
import com.example.application.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepo : JpaRepository<User, UUID> {
}