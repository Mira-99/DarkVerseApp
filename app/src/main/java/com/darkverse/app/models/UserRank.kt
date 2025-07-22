package com.darkverse.app.models

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
    DARK_LORD("سيد الظلام", "#000000", 100),
    ADMIN("مشرف", "#FF0000", 999);

    companion object {
        fun fromName(name: String): UserRank {
            return values().find { it.name == name } ?: NEWBIE
        }
    }
}
