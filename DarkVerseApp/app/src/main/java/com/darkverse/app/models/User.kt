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
    val countryCode: String = "",
    val nativeLanguages: List<String> = emptyList(), // Languages user speaks natively
    val learningLanguages: List<String> = emptyList(), // Languages user wants to learn
    val age: Int = 18,
    val gender: String = "", // "male", "female", "other"
    val interests: List<String> = emptyList(), // Anime genres, hobbies, etc.
    val lookingFor: String = "language_exchange", // "language_exchange", "friendship", "anime_discussion"
    val isAvailableForMatching: Boolean = true
)

enum class UserRank(val displayName: String, val color: String, val minLevel: Int) {
    NEWBIE("مبتدئ", "#8E8E93", 1),
    OTAKU("أوتاكو", "#007AFF", 5),
    SENPAI("سينباي", "#34C759", 15),
    SENSEI("سينسي", "#FF9500", 30),
    LEGEND("أسطورة", "#FF3B30", 50),
    SHADOW_MASTER("سيد الظلال", "#5856D6", 75),
    DARK_LORD("سيد الظلام", "#000000", 100)
}

