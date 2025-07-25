package com.darkverse.app.models

data class Post(
    val postId: String = "",            // ID المنشور (مفيد لاحقًا للحذف أو التعديل)
    val userId: String = "",            // UID من Firebase
    val username: String = "",          // اسم المستخدم الظاهر
    val userProfileImage: String = "",  // رابط صورة البروفايل
    val content: String = "",           // نص المنشور
    val imageUrl: String = "",          // رابط صورة المنشور (اختياري)
    val timestamp: Long = 0L            // الوقت
)
