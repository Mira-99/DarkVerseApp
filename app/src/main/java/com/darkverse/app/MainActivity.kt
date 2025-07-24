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
        // مثال على بيانات وهمية
        postList.add(Post("ميرا", 0, 0, "أول بوست إلي هون 🎉"))
        postList.add(Post("نور", 0, 0, "كيفكن يا جماعة؟"))

        postAdapter.notifyDataSetChanged()
    }
}
