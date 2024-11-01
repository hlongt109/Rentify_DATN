package com.rentify.user.app.model

// User.kt
data class User(
  val id: String,
  val username: String,
  val email: String,
  val password: String,
  val name: String? = null
)
