package com.darkverse.app.models

data class Post(
    val username: String,
    val caption: String,
    val timestamp: String,
    val userRank: String,
    val userCountry: String,
    val userGender: String
)
