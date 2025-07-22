package com.darkverse.app.ranks

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.darkverse.app.databinding.ActivityRanksAchievementsBinding
import com.darkverse.app.models.AchievementsData
import com.darkverse.app.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class RanksAchievementsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRanksAchievementsBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var achievementsAdapter: AchievementsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRanksAchievementsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        setupRecyclerView()
        loadUserData()
    }

    private fun setupRecyclerView() {
        achievementsAdapter = AchievementsAdapter(AchievementsData.achievements)
        binding.achievementsRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.achievementsRecyclerView.adapter = achievementsAdapter
    }

    private fun loadUserData() {
        val userId = auth.currentUser?.uid
        if (userId != null) {
            database.reference.child("users").child(userId).get()
                .addOnSuccessListener { snapshot ->
                    val user = snapshot.getValue(User::class.java)
                    if (user != null) {
                        displayUserRankAndLevel(user)
                        // Update achievements based on user progress
                        updateAchievements(user)
                    }
                }
                .addOnFailureListener {
                    // Handle error
                }
        }
    }

    private fun displayUserRankAndLevel(user: User) {
        binding.currentRankTextView.text = user.rank.displayName
        binding.currentLevelTextView.text = user.level.toString()
    }

    private fun updateAchievements(user: User) {
        // This would typically check user's progress against achievement requirements
        // and update the achievements list accordingly
        // For now, we'll just refresh the adapter
        achievementsAdapter.notifyDataSetChanged()
    }
}

