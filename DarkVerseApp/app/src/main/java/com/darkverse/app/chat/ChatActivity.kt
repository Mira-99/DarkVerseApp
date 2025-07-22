package com.darkverse.app.chat

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.darkverse.app.databinding.ActivityChatBinding
import com.darkverse.app.models.Message
import com.darkverse.app.models.MessageType
import com.darkverse.app.models.UserRank
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var messagesAdapter: MessagesAdapter
    private val messagesList = mutableListOf<Message>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        setupRecyclerView()
        setupMessageListener()
        setupSendButton()
    }

    private fun setupRecyclerView() {
        messagesAdapter = MessagesAdapter(messagesList, auth.currentUser?.uid ?: "")
        binding.chatRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.chatRecyclerView.adapter = messagesAdapter
    }

    private fun setupMessageListener() {
        // For simplicity, let's assume a single chat room for now
        // In a real app, you'd pass a chat ID via intent
        val chatId = "general_chat_room"

        database.reference.child("chats").child(chatId).child("messages")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    messagesList.clear()
                    for (messageSnapshot in snapshot.children) {
                        val message = messageSnapshot.getValue(Message::class.java)
                        message?.let { messagesList.add(it) }
                    }
                    messagesAdapter.notifyDataSetChanged()
                    binding.chatRecyclerView.scrollToPosition(messagesList.size - 1)
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@ChatActivity, "فشل تحميل الرسائل: ${error.message}", Toast.LENGTH_SHORT).show()
                }
            })
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
            val messageId = database.reference.child("chats").child("general_chat_room").child("messages").push().key
            val senderId = currentUser.uid
            // In a real app, you'd fetch senderName and senderRank from the User object
            val senderName = currentUser.displayName ?: "مستخدم مجهول"
            val senderRank = UserRank.NEWBIE // Placeholder

            val message = Message(
                id = messageId ?: "",
                senderId = senderId,
                senderName = senderName,
                senderRank = senderRank,
                content = content,
                type = MessageType.TEXT,
                timestamp = System.currentTimeMillis()
            )

            messageId?.let {
                database.reference.child("chats").child("general_chat_room").child("messages").child(it).setValue(message)
                    .addOnFailureListener {
                        Toast.makeText(this, "فشل إرسال الرسالة: ${it.message}", Toast.LENGTH_SHORT).show()
                    }
            }
        } else {
            Toast.makeText(this, "يجب تسجيل الدخول لإرسال الرسائل", Toast.LENGTH_SHORT).show()
        }
    }
}

