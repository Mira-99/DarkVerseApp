package com.darkverse.app.models

data class Post(
    val username: String,       // اسم المستخدم
    val caption: String,        // نص المنشور
    val timestamp: String,      // وقت النشر (مثلاً "قبل ساعة")
    val userRank: String,       // رتبة المستخدم (مثلاً "شيطان مبتدئ")
    val userCountry: String,    // بلد المستخدم (مثلاً "كردستان")
    val userGender: String      // نوع الجنس (مثلاً "ذكر")
)
