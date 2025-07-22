package com.darkverse.app.ai

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.darkverse.app.chat.MessagesAdapter
import com.darkverse.app.databinding.ActivityAiChatBinding
import com.darkverse.app.models.Message
import com.darkverse.app.models.MessageType
import com.darkverse.app.models.UserRank
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class AIChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAiChatBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var messagesAdapter: MessagesAdapter
    private val messagesList = mutableListOf<Message>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAiChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        setupRecyclerView()
        setupSendButton()
        sendWelcomeMessage()
    }

    private fun setupRecyclerView() {
        messagesAdapter = MessagesAdapter(messagesList, auth.currentUser?.uid ?: "")
        binding.aiChatRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.aiChatRecyclerView.adapter = messagesAdapter
    }

    private fun setupSendButton() {
        binding.sendButton.setOnClickListener {
            val messageContent = binding.messageEditText.text.toString().trim()
            if (messageContent.isNotEmpty()) {
                sendMessage(messageContent)
                binding.messageEditText.text?.clear()
            }
        }
    }

    private fun sendMessage(content: String) {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val messageId = database.reference.child("ai_chats").child(currentUser.uid).push().key
            val senderId = currentUser.uid
            val senderName = currentUser.displayName ?: "أنت"

            val userMessage = Message(
                id = messageId ?: "",
                senderId = senderId,
                senderName = senderName,
                senderRank = UserRank.NEWBIE, // Placeholder
                content = content,
                type = MessageType.TEXT,
                timestamp = System.currentTimeMillis()
            )

            messageId?.let {
                database.reference.child("ai_chats").child(currentUser.uid).child(it).setValue(userMessage)
                    .addOnSuccessListener { addMessageToList(userMessage); simulateAIResponse(content) }
                    .addOnFailureListener { Toast.makeText(this, "فشل إرسال الرسالة: ${it.message}", Toast.LENGTH_SHORT).show() }
            }
        } else {
            Toast.makeText(this, "يجب تسجيل الدخول لإرسال الرسائل", Toast.LENGTH_SHORT).show()
        }
    }

    private fun simulateAIResponse(userMessage: String) {
        val aiResponseContent = when {
            userMessage.contains("أنمي", ignoreCase = true) -> "أنا أحب الأنمي! ما هو الأنمي المفضل لديك؟"
            userMessage.contains("مرحباً", ignoreCase = true) || userMessage.contains("أهلاً", ignoreCase = true) -> "أهلاً بك في DarkVerse! كيف يمكنني مساعدتك اليوم؟"
            userMessage.contains("كيف حالك", ignoreCase = true) -> "أنا ذكاء اصطناعي، لا أشعر بالتعب أو الملل. أنا هنا لخدمتك!"
            else -> "هذا مثير للاهتمام! هل يمكنك أن تخبرني المزيد؟"
        }

        val aiMessageId = database.reference.child("ai_chats").child(auth.currentUser?.uid ?: "").push().key
        val aiMessage = Message(
            id = aiMessageId ?: "",
            senderId = "AI_BOT",
            senderName = "DarkVerse AI",
            senderRank = UserRank.ADMIN, // AI has admin rank
            content = aiResponseContent,
            type = MessageType.AI_RESPONSE,
            timestamp = System.currentTimeMillis() + 1000 // Simulate delay
        )

        aiMessageId?.let {
            database.reference.child("ai_chats").child(auth.currentUser?.uid ?: "").child(it).setValue(aiMessage)
                .addOnSuccessListener { addMessageToList(aiMessage) }
                .addOnFailureListener { Toast.makeText(this, "فشل إرسال رد الذكاء الاصطناعي: ${it.message}", Toast.LENGTH_SHORT).show() }
        }
    }

    private fun sendWelcomeMessage() {
        val welcomeMessage = Message(
            id = "welcome_ai",
            senderId = "AI_BOT",
            senderName = "DarkVerse AI",
            senderRank = UserRank.ADMIN,
            content = "مرحباً بك في دردشة الذكاء الاصطناعي! أنا هنا لمساعدتك في أي شيء يتعلق بالأنمي أو التطبيق.",
            type = MessageType.AI_RESPONSE,
            timestamp = System.currentTimeMillis()
        )
        addMessageToList(welcomeMessage)
    }

    private fun addMessageToList(message: Message) {
        messagesList.add(message)
        messagesAdapter.notifyItemInserted(messagesList.size - 1)
        binding.aiChatRecyclerView.scrollToPosition(messagesList.size - 1)
    }
}

