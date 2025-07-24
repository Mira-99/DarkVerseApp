package com.darkverse.app.models

data class Post(
    val username: String,        // اسم المستخدم
    val profileImage: Int,       // صورة البروفايل (لو عندك)
    val postImageRes: Int,       // صورة المنشور (لو عندك)
    val caption: String,         // نص المنشور
    val timestamp: String        // وقت المنشور، مثلاً "قبل ساعة"
)
