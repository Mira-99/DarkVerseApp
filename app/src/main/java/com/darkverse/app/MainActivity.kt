package com.darkverse.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.darkverse.app.adapters.PostAdapter
import com.darkverse.app.models.Post

class MainActivity : AppCompatActivity() {

    private lateinit var postRecyclerView: RecyclerView
    private lateinit var postAdapter: PostAdapter
    private val postList = mutableListOf<Post>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        postRecyclerView = findViewById(R.id.recyclerView)
        postRecyclerView.layoutManager = LinearLayoutManager(this)
        postAdapter = PostAdapter(postList)
        postRecyclerView.adapter = postAdapter

        loadPosts()
    }

    private fun loadPosts() {
        postList.clear()

        postList.add(
            Post(
                username = "ميرا",
                caption = "أول بوست إلي هون 🎉",
                timestamp = "قبل ساعة"
            )
        )

        postList.add(
            Post(
                username = "نور",
                caption = "كيفكن يا جماعة؟",
                timestamp = "قبل 30 دقيقة"
            )
        )

        postList.add(
            Post(
                username = "ميران",
                caption = "DarkVerse صار شغال 🔥",
                timestamp = "الآن"
            )
        )

        postAdapter.notifyDataSetChanged()
    }
}
