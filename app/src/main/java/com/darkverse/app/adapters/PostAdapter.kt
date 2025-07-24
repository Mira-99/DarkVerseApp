package com.darkverse.app.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.darkverse.app.R
import com.darkverse.app.models.Post

class PostAdapter(private val postList: List<Post>) : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    inner class PostViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val usernameText: TextView = view.findViewById(R.id.usernameText)
        val descriptionText: TextView = view.findViewById(R.id.descriptionText)
        val timestampText: TextView? = view.findViewById(R.id.timestampText) // لو مستخدم الوقت
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = postList[position]
        holder.usernameText.text = post.username
        holder.descriptionText.text = post.caption // استخدم descriptionText بدل captionText
        holder.timestampText?.text = post.timestamp
    }

    override fun getItemCount(): Int = postList.size
}
