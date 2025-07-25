package com.darkverse.app.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.darkverse.app.R
import com.darkverse.app.models.Post
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class PostAdapter(private val postList: List<Post>) : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val usernameText: TextView = itemView.findViewById(R.id.usernameTextView)
        val contentText: TextView = itemView.findViewById(R.id.contentTextView)
        val timeText: TextView = itemView.findViewById(R.id.dateTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = postList[position]
        holder.usernameText.text = post.username
        holder.contentText.text = post.content

        val now = System.currentTimeMillis()
        val diff = now - post.timestamp

        holder.timeText.text = when {
            diff < TimeUnit.MINUTES.toMillis(1) -> "الآن"
            diff < TimeUnit.HOURS.toMillis(1) -> "قبل ${TimeUnit.MILLISECONDS.toMinutes(diff)} دقيقة"
            diff < TimeUnit.DAYS.toMillis(1) -> "قبل ${TimeUnit.MILLISECONDS.toHours(diff)} ساعة"
            else -> {
                val sdf = SimpleDateFormat("dd MMM، HH:mm", Locale.getDefault())
                sdf.format(Date(post.timestamp))
            }
        }
    }

    override fun getItemCount(): Int = postList.size
}
