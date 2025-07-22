package com.darkverse.app.models

data class User(
    val uid: String = "",
    val username: String = "",
    val email: String = "",
    val displayName: String = "",
    val profileImageUrl: String = "",
    val bio: String = "",
    val rank: UserRank = UserRank.NEWBIE,
    val level: Int = 1,
    val experience: Int = 0,
    val joinDate: Long = System.currentTimeMillis(),
    val lastSeen: Long = System.currentTimeMillis(),
    val isOnline: Boolean = false,
    val favoriteAnime: List<String> = emptyList(),
    val badges: List<String> = emptyList(),
    val isPremium: Boolean = false,
    val premiumExpiry: Long = 0,
    val language: String = "ar",
    val darkTheme: Boolean = true,
    val countryCode: String = "", // مثال: "SY" أو "TR" أو "IQ"
    val nativeLanguages: List<String> = emptyList(), // لغات المستخدم الأصلية
    val learningLanguages: List<String> = emptyList(), // لغات يود تعلمها
    val age: Int = 18,
    val gender: String = "", // "male", "female", "other"
    val interests: List<String> = emptyList(), // أنواع الأنمي، هوايات...
    val lookingFor: String = "language_exchange", // أو: friendship, anime_discussion
    val isAvailableForMatching: Boolean = true, // هل مستعد للتطابق؟
    val flagEmoji: String = "", // لتخزين الإيموجي الخاص بالعلم
    val isProfilePublic: Boolean = true, // خصوصية الملف الشخصي
    val followersCount: Int = 0, // عدد المتابعين
    val followingCount: Int = 0 // عدد يلي بيتابعهم
)

enum class UserRank(
    val displayName: String,
    val color: String,
    val minLevel: Int
) {
    NEWBIE("مبتدئ", "#8E8E93", 1),
    OTAKU("أوتاكو", "#007AFF", 5),
    SENPAI("سينباي", "#34C759", 15),
    SENSEI("سينسي", "#FF9500", 30),
    LEGEND("أسطورة", "#FF3B30", 50),
    SHADOW_MASTER("سيد الظلال", "#5856D6", 75),
    DARK_LORD("سيد الظلام", "#000000", 100)
}
