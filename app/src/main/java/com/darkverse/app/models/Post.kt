package com.darkverse.app.models

data class Post(
    val username: String,      // اسم المستخدم
    val caption: String,       // نص المنشور
    val timestamp: String      // وقت المنشور، مثلاً "قبل ساعة"
)
