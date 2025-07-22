package com.darkverse.app.profile

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.darkverse.app.databinding.ActivityProfileBinding
import com.darkverse.app.models.User
import com.darkverse.app.models.UserRank
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
                    }
                }
        }
    }

    private fun displayUserProfile(user: User) {
        binding.usernameTextView.text = user.displayName
        binding.bioTextView.text = user.bio.ifEmpty { "لا توجد سيرة ذاتية." }

        // نحول اسم الرتبة إلى كائن UserRank
        val userRank = try {
            UserRank.valueOf(user.rank)
        } catch (e: Exception) {
            UserRank.NEWBIE // افتراضي إذا ما تعرف الرتبة
        }

        // عرض الرتبة ولونها
        val rankText = "الرتبة: ${userRank.displayName}"
        binding.rankTextView.text = rankText

        try {
            val color = Color.parseColor(userRank.color)
            binding.rankTextView.setTextColor(color)
        } catch (e: Exception) {
            binding.rankTextView.setTextColor(Color.GRAY) // لون افتراضي لو صار خطأ
        }

        // يمكنك لاحقًا إضافة تحميل صورة البروفايل باستخدام Glide
    }
}
