package com.dwi.expensetracker.controller

import com.dwi.expensetracker.service.AuthService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses

@RestController
@RequestMapping("/api/auth")
class AuthController(private val authService: AuthService) {

    @Operation(summary = "Register a new user")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "User registered successfully"),
            ApiResponse(responseCode = "400", description = "Invalid input", content = [Content(schema = Schema(hidden = true))])
        ]
    )
    @PostMapping("/register")
    fun register(@RequestParam username: String, @RequestParam password: String): ResponseEntity<String> {
        authService.register(username, password)
        return ResponseEntity.ok("User registered successfully")
    }

    @Operation(summary = "Login with existing user credentials")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Login successful"),
            ApiResponse(responseCode = "400", description = "Invalid username or password", content = [Content(schema = Schema(hidden = true))])
        ]
    )
    @PostMapping("/login")
    fun login(@RequestParam username: String, @RequestParam password: String): ResponseEntity<String> {
        return if (authService.login(username, password)) {
            ResponseEntity.ok("Login successful")
        } else {
            ResponseEntity.badRequest().body("Invalid username or password")
        }
    }
}
