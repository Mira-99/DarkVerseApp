package com.darkverse.app.models

import com.google.firebase.firestore.IgnoreExtraProperties
import java.io.Serializable

@IgnoreExtraProperties
data class User(
    val uid: String = "",
    val username: String = "",
    val email: String = "",
    val displayName: String = "",
    val profileImageUrl: String = "",
    val bio: String = "",
    val rank: String = UserRank.NEWBIE.name, // تخزين اسم الرتبة كسلسلة نصية
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
    val isAvailableForMatching: Boolean = true,
    val flagEmoji: String = "",
    val isProfilePublic: Boolean = true,
    val followersCount: Int = 0,
    val followingCount: Int = 0
) : Serializable
