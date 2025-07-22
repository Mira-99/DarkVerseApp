package com.darkverse.app.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.darkverse.app.databinding.ItemMessageBinding
import com.darkverse.app.models.Message
import com.darkverse.app.models.MessageType
import java.text.SimpleDateFormat
import java.util.*

class MessagesAdapter(
    private val messages: List<Message>,
    private val currentUserId: String
) : RecyclerView.Adapter<MessagesAdapter.MessageViewHolder>() {

    private val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val binding = ItemMessageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MessageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.bind(messages[position])
    }

    override fun getItemCount(): Int = messages.size

    inner class MessageViewHolder(private val binding: ItemMessageBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(message: Message) {
            binding.senderName.text = message.senderName
            binding.messageTime.text = dateFormat.format(Date(message.timestamp))
            
            when (message.type) {
                MessageType.TEXT -> {
                    binding.messageContent.text = message.content
                    binding.messageContent.visibility = View.VISIBLE
                    binding.messageImage.visibility = View.GONE
                }
                MessageType.IMAGE -> {
                    binding.messageContent.text = if (message.content.isNotEmpty()) message.content else "صورة"
                    binding.messageContent.visibility = View.VISIBLE
                    binding.messageImage.visibility = View.VISIBLE
                    
                    // Load image/GIF using Glide
                    Glide.with(binding.root.context)
                        .load(message.imageUrl)
                        .into(binding.messageImage)
                }
                MessageType.AUDIO -> {
                    binding.messageContent.text = "رسالة صوتية"
                    binding.messageContent.visibility = View.VISIBLE
                    binding.messageImage.visibility = View.GONE
                }
                MessageType.AI_RESPONSE -> {
                    binding.messageContent.text = message.content
                    binding.messageContent.visibility = View.VISIBLE
                    binding.messageImage.visibility = View.GONE
                    // You can add special styling for AI responses here
                }
            }
            
            // Customize appearance based on whether it's the current user's message
            if (message.senderId == currentUserId) {
                // Style for current user's messages (right-aligned, different color, etc.)
                binding.root.setPadding(64, 8, 8, 8)
            } else {
                // Style for other users' messages (left-aligned)
                binding.root.setPadding(8, 8, 64, 8)
            }
        }
    }
}

