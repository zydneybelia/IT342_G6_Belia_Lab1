package com.example.quickbite_mobile.data.model

// ── Register ──────────────────────────────────────────────
data class RegisterRequest(
    val firstname: String,
    val lastname: String,
    val email: String,
    val password: String
)

data class RegisterResponse(
    val message: String
)

// ── Login ─────────────────────────────────────────────────
data class LoginRequest(
    val email: String,
    val password: String
)

data class LoginResponse(
    val token: String,
    val type: String,
    val id: String,
    val firstname: String,
    val lastname: String,
    val email: String,
    val role: String
)

// ── Error ─────────────────────────────────────────────────
data class ErrorResponse(
    val message: String
)