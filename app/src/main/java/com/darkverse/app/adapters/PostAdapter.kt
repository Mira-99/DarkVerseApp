package com.darkverse.app.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.darkverse.app.R
import com.darkverse.app.models.Post

class PostAdapter(private val postList: List<Post>) : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    inner class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val usernameText: TextView = itemView.findViewById(R.id.usernameText)
        val profileImage: ImageView = itemView.findViewById(R.id.profileImage)
        val postImage: ImageView = itemView.findViewById(R.id.postImage)
        val captionText: TextView = itemView.findViewById(R.id.captionText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = postList[position]
        holder.usernameText.text = post.username
        holder.captionText.text = post.caption

        // بدل الصور برموز بديلة (مثلاً خلفيات ملونة من الموارد)
        holder.profileImage.setImageResource(R.drawable.ic_profile_placeholder)
        holder.postImage.setImageResource(R.drawable.ic_post_placeholder)
    }

    override fun getItemCount(): Int = postList.size
}
