package com.darkverse.app.models

data class Post(
    val username: String = "",
    val content: String = "",
    val mediaUrl: String = "",       // رابط الصورة أو الفيديو
    val mediaType: String = "",      // نوع الوسائط: "image" أو "video" أو "" لو ما في وسائط
    val timestamp: Long = 0L
)
