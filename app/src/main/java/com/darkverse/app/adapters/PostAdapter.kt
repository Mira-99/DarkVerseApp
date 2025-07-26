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
import java.util.concurrent.TimeUnit

class PostAdapter(private val postList: List<Post>) : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    inner class PostViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val usernameText: TextView = view.findViewById(R.id.usernameTextView)
        val contentText: TextView = view.findViewById(R.id.contentTextView)
        val postImageView: ImageView = view.findViewById(R.id.postImageView)
        val timeText: TextView = view.findViewById(R.id.dateTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = postList[position]

        // الاسم والمحتوى
        holder.usernameText.text = post.username.ifEmpty { "مجهول" }
        holder.contentText.text = post.content.ifBlank { "بدون محتوى" }

        // عرض الصورة لو موجودة
        if (post.mediaUrl.isNotEmpty() && post.mediaType == "image") {
            holder.postImageView.visibility = View.VISIBLE
            Glide.with(holder.itemView.context)
                .load(post.mediaUrl)
                .placeholder(R.color.gray) // لون رمادي معرّف في colors.xml
                .into(holder.postImageView)
        } else {
            holder.postImageView.visibility = View.GONE
        }

        // تنسيق الوقت
        val now = System.currentTimeMillis()
        val diff = now - post.timestamp

        holder.timeText.text = when {
            diff < TimeUnit.MINUTES.toMillis(1) -> "الآن"
            diff < TimeUnit.HOURS.toMillis(1) -> "قبل ${TimeUnit.MILLISECONDS.toMinutes(diff)} دقيقة"
            diff < TimeUnit.DAYS.toMillis(1) -> "قبل ${TimeUnit.MILLISECONDS.toHours(diff)} ساعة"
            diff < TimeUnit.DAYS.toMillis(7) -> "قبل ${TimeUnit.MILLISECONDS.toDays(diff)} يوم"
            else -> {
                val sdf = SimpleDateFormat("dd MMM yyyy، HH:mm", Locale("ar"))
                sdf.format(Date(post.timestamp))
            }
        }
    }

    override fun getItemCount(): Int = postList.size
}
