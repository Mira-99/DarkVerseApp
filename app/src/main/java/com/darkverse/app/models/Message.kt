package com.darkverse.app.models

data class Message(
    val id: String = "",
    val senderId: String = "",
    val senderName: String = "",
    val senderRank: UserRank = UserRank.NEWBIE,
    val content: String = "",
    val type: MessageType = MessageType.TEXT,
    val timestamp: Long = System.currentTimeMillis(),
    val chatId: String = "",
    val imageUrl: String = "",
    val audioUrl: String = "",
    val isRead: Boolean = false,
    val reactions: Map<String, String> = emptyMap() // userId to emoji
)

enum class MessageType {
    TEXT,
    IMAGE,
    AUDIO,
    AI_RESPONSE
}

data class Chat(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val type: ChatType = ChatType.PUBLIC,
    val createdBy: String = "",
    val createdAt: Long = System.currentTimeMillis(),
    val lastMessage: Message? = null,
    val participants: List<String> = emptyList(),
    val isActive: Boolean = true
)

enum class ChatType {
    PUBLIC,
    PRIVATE,
    GROUP,
    AI_CHAT
}

