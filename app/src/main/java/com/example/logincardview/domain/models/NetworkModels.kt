package com.example.logincardview.model

data class UserRequest(
    val email: String,
    val password: String
)

data class AuthResponse(
    val token: String
)
