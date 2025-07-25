package com.darkverse.app.models

data class Post(
    val username: String = "",
    val content: String = "",
    val imageUrl: String = "",
    val timestamp: Long = 0L
)
