package com.dwi.expensetracker.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
class SecurityConfig {
    @Bean
    fun passwordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .authorizeHttpRequests { authz ->
                authz
                    .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/api/**").permitAll() // Izinkan akses Swagger UI dan API Docs
                    .anyRequest().authenticated() // Mengamankan endpoint lainnya
            }
            .csrf { csrf -> csrf.disable() } // Menonaktifkan CSRF untuk sementara

        return http.build()
    }
}
