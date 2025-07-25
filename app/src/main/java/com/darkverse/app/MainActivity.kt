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
    private val postList = ArrayList<Post>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        postRecyclerView = findViewById(R.id.recyclerViewPosts)
        postRecyclerView.layoutManager = LinearLayoutManager(this)

        // بيانات تجريبية مؤقتة
        postList.add(Post("ميران", "هذا أول منشور في DarkVerse", "https://example.com/image1.jpg"))
        postList.add(Post("ريما", "مرحبا بكم في مجتمع الظلال", "https://example.com/image2.jpg"))

        postAdapter = PostAdapter(postList)
        postRecyclerView.adapter = postAdapter
    }
}
