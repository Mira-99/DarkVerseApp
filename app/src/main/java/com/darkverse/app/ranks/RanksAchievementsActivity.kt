package com.darkverse.app.ranks

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.darkverse.app.databinding.ActivityRanksAchievementsBinding
import com.darkverse.app.models.AchievementsData
import com.darkverse.app.models.User
import com.darkverse.app.models.UserRank
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
                        updateAchievements(user)
                    }
                }
                .addOnFailureListener {
                    // Handle error if needed
                }
        }
    }

    private fun displayUserRankAndLevel(user: User) {
        val userRank = UserRank.fromName(user.rank)
        binding.currentRankTextView.text = userRank.displayName
        binding.currentLevelTextView.text = user.level.toString()
    }

    private fun updateAchievements(user: User) {
        // تحديث الإنجازات حسب بيانات المستخدم (لو عندك منطق معين)
        achievementsAdapter.notifyDataSetChanged()
    }
}
