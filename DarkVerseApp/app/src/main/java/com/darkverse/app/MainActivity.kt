package com.darkverse.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.darkverse.app.chat.MatchingActivity
import com.darkverse.app.databinding.ActivityMainBinding
import com.darkverse.app.profile.ProfileActivity
import com.darkverse.app.ranks.RanksAchievementsActivity
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityMainBinding
    private lateinit var firebaseAnalytics: FirebaseAnalytics
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseDatabase: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        // Initialize Firebase components
        firebaseAnalytics = FirebaseAnalytics.getInstance(this)
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseDatabase = FirebaseDatabase.getInstance()
        
        // Log an event to Firebase Analytics
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "main_activity")
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "MainActivity Opened")
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle)

        binding.profileButton.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        binding.matchingButton.setOnClickListener {
            startActivity(Intent(this, MatchingActivity::class.java))
        }

        binding.ranksAchievementsButton.setOnClickListener {
            startActivity(Intent(this, RanksAchievementsActivity::class.java))
        }
    }
}

