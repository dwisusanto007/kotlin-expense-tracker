package com.dwi.expensetracker.service

import com.dwi.expensetracker.entity.User
import com.dwi.expensetracker.repository.UserRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val passwordEncoder: BCryptPasswordEncoder
) {
    fun register(username: String, password: String): User {
        if (userRepository.findByUsername(username).isPresent) {
            throw IllegalArgumentException("Username is already taken")
        }
        val encodedPassword = passwordEncoder.encode(password)
        val user = User(username = username, password = encodedPassword)
        return userRepository.save(user)
    }

    fun login(username: String, password: String): Boolean {
        val user = userRepository.findByUsername(username).orElseThrow {
            IllegalArgumentException("Invalid username or password")
        }
        return passwordEncoder.matches(password, user.password)
    }
}
