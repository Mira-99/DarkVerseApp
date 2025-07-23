package com.darkverse.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.darkverse.app.adapters.StoryAdapter
import com.darkverse.app.databinding.ActivityHomeBinding
import com.darkverse.app.models.Story

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val stories = listOf(
            Story("ميران", R.drawable.sample_profile),
            Story("ريما", R.drawable.sample_profile),
            Story("سيد الظلال", R.drawable.sample_profile),
            Story("زائر الليل", R.drawable.sample_profile)
        )

        binding.storyRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.storyRecyclerView.adapter = StoryAdapter(stories)
    }
}
