package com.darkverse.app.models

data class Achievement(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val iconUrl: String = "",
    val isUnlocked: Boolean = false,
    val unlockedDate: Long = 0,
    val category: AchievementCategory = AchievementCategory.GENERAL,
    val rarity: AchievementRarity = AchievementRarity.COMMON,
    val experienceReward: Int = 0,
    val requirements: Map<String, Int> = emptyMap() // e.g., "messages_sent" -> 100
)

enum class AchievementCategory(val displayName: String) {
    GENERAL("عام"),
    SOCIAL("اجتماعي"),
    ANIME("أنمي"),
    LANGUAGE("لغة"),
    PREMIUM("مميز")
}

enum class AchievementRarity(val displayName: String, val color: String) {
    COMMON("شائع", "#8E8E93"),
    RARE("نادر", "#007AFF"),
    EPIC("ملحمي", "#AF52DE"),
    LEGENDARY("أسطوري", "#FF9500"),
    MYTHIC("خرافي", "#FF3B30")
}

object AchievementsData {
    val achievements = listOf(
        Achievement(
            id = "first_message",
            title = "أول رسالة",
            description = "أرسل رسالتك الأولى في DarkVerse",
            category = AchievementCategory.SOCIAL,
            rarity = AchievementRarity.COMMON,
            experienceReward = 10,
            requirements = mapOf("messages_sent" to 1)
        ),
        Achievement(
            id = "social_butterfly",
            title = "فراشة اجتماعية",
            description = "أرسل 100 رسالة",
            category = AchievementCategory.SOCIAL,
            rarity = AchievementRarity.RARE,
            experienceReward = 50,
            requirements = mapOf("messages_sent" to 100)
        ),
        Achievement(
            id = "anime_lover",
            title = "عاشق الأنمي",
            description = "أضف 10 أنمي إلى قائمة المفضلة",
            category = AchievementCategory.ANIME,
            rarity = AchievementRarity.RARE,
            experienceReward = 30,
            requirements = mapOf("favorite_anime_count" to 10)
        ),
        Achievement(
            id = "language_master",
            title = "سيد اللغات",
            description = "تعلم 3 لغات مختلفة",
            category = AchievementCategory.LANGUAGE,
            rarity = AchievementRarity.EPIC,
            experienceReward = 100,
            requirements = mapOf("languages_learned" to 3)
        ),
        Achievement(
            id = "premium_member",
            title = "عضو مميز",
            description = "احصل على العضوية المميزة",
            category = AchievementCategory.PREMIUM,
            rarity = AchievementRarity.LEGENDARY,
            experienceReward = 200,
            requirements = mapOf("is_premium" to 1)
        ),
        Achievement(
            id = "dark_lord",
            title = "سيد الظلام",
            description = "وصل إلى المستوى 50",
            category = AchievementCategory.GENERAL,
            rarity = AchievementRarity.MYTHIC,
            experienceReward = 500,
            requirements = mapOf("level" to 50)
        )
    )
    
    fun getAchievementById(id: String): Achievement? {
        return achievements.find { it.id == id }
    }
    
    fun getAchievementsByCategory(category: AchievementCategory): List<Achievement> {
        return achievements.filter { it.category == category }
    }
}

