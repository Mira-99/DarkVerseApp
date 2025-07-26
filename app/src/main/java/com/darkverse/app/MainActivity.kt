package com.darkverse.app

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.darkverse.app.adapters.PostAdapter
import com.darkverse.app.databinding.ActivityMainBinding
import com.darkverse.app.models.Post

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var postAdapter: PostAdapter
    private val postList = mutableListOf<Post>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        loadSamplePosts()

        // زر إضافة منشور
        binding.fabAddPost.setOnClickListener {
            val intent = Intent(this, AddPostActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupRecyclerView() {
        postAdapter = PostAdapter(postList)
        binding.recyclerViewPosts.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewPosts.adapter = postAdapter
    }

    private fun loadSamplePosts() {
        postList.add(
            Post(
                username = "Miran",
                content = "أهلاً بكم في تطبيق دارك فيرس 👹",
                imageUrl = "",
                timestamp = System.currentTimeMillis()
            )
        )
        postList.add(
            Post(
                username = "ShadowKing",
                content = "استعدوا للظلام..",
                imageUrl = "https://picsum.photos/400",
                timestamp = System.currentTimeMillis()
            )
        )
        postList.add(
            Post(
                username = "Reaper",
                content = "من هنا يبدأ كل شيء 🔥",
                imageUrl = "",
                timestamp = System.currentTimeMillis()
            )
        )
        postAdapter.notifyDataSetChanged()
    }
}
