package com.darkverse.app.profile

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.darkverse.app.databinding.ActivityProfileBinding
import com.darkverse.app.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        loadUserProfile()

        binding.editProfileButton.setOnClickListener {
            // TODO: Implement edit profile functionality
            // For now, just show a toast
            // Toast.makeText(this, "تعديل الملف الشخصي قيد التطوير", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadUserProfile() {
        val userId = auth.currentUser?.uid
        if (userId != null) {
            database.reference.child("users").child(userId).get()
                .addOnSuccessListener {
                    val user = it.getValue(User::class.java)
                    if (user != null) {
                        displayUserProfile(user)
                    } else {
                        // Handle case where user data is not found
                    }
                }
                .addOnFailureListener {
                    // Handle error
                }
        }
    }

    private fun displayUserProfile(user: User) {
        binding.usernameTextView.text = user.displayName
        binding.rankTextView.text = "الرتبة: ${user.rank.displayName}"
        binding.bioTextView.text = user.bio.ifEmpty { "لا توجد سيرة ذاتية." }
        // Load profile image using a library like Glide or Picasso
        // For now, default image is used
    }
}

