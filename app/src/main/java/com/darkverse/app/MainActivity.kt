package com.darkverse.app

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
    }

    private fun setupRecyclerView() {
        postAdapter = PostAdapter(postList)
        binding.recyclerViewPosts.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewPosts.adapter = postAdapter
    }

    private fun loadSamplePosts() {
        postList.add(Post("Miran", "أهلاً بكم في تطبيق دارك فيرس 👹", "", System.currentTimeMillis()))
        postList.add(Post("ShadowKing", "استعدوا للظلام..", "https://picsum.photos/400", System.currentTimeMillis()))
        postList.add(Post("Reaper", "من هنا يبدأ كل شيء 🔥", "", System.currentTimeMillis()))
        postAdapter.notifyDataSetChanged()
    }
}
