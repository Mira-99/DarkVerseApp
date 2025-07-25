package com.darkverse.app.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.darkverse.app.R
import com.darkverse.app.models.Post
import java.text.SimpleDateFormat
import java.util.*

class PostAdapter(private val postList: List<Post>) : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val usernameText: TextView = itemView.findViewById(R.id.textUsername)
        val contentText: TextView = itemView.findViewById(R.id.textContent)
        val postImage: ImageView = itemView.findViewById(R.id.imagePost)
        val timeText: TextView = itemView.findViewById(R.id.textTimestamp)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = postList[position]
        holder.usernameText.text = post.username
        holder.contentText.text = post.content

        if (post.imageUrl.isNotEmpty()) {
            Glide.with(holder.itemView.context).load(post.imageUrl).into(holder.postImage)
        } else {
            holder.postImage.visibility = View.GONE
        }

        val sdf = SimpleDateFormat("dd MMM, HH:mm", Locale.getDefault())
        val date = Date(post.timestamp)
        holder.timeText.text = sdf.format(date)
    }

    override fun getItemCount(): Int = postList.size
}
