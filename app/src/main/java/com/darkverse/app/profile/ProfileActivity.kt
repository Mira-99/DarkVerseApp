package com.darkverse.app.profile

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
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
            // TODO: افتح شاشة تعديل البروفايل
        }
    }

    private fun loadUserProfile() {
        val userId = auth.currentUser?.uid
        if (userId != null) {
            database.reference.child("users").child(userId).get()
                .addOnSuccessListener { snapshot ->
                    val user = snapshot.getValue(User::class.java)
                    if (user != null) {
                        displayUserProfile(user)
                    } else {
                        binding.usernameTextView.text = "حدث خطأ في تحميل البيانات."
                    }
                }
                .addOnFailureListener {
                    binding.usernameTextView.text = "تعذر الوصول إلى قاعدة البيانات."
                }
        }
    }

    private fun displayUserProfile(user: User) {
        binding.usernameTextView.text = user.displayName
        binding.bioTextView.text = user.bio.ifEmpty { "لا توجد سيرة ذاتية." }

        // عرض صورة البروفايل
        if (!user.profileImageUrl.isNullOrEmpty()) {
            Glide.with(this)
                .load(user.profileImageUrl)
                .into(binding.profileImageView)
        }

        // عرض البلد، اللغة، الجنس
        binding.countryTextView.text = "البلد: ${user.country.ifEmpty { "غير محدد" }}"
        binding.languageTextView.text = "اللغة: ${user.language.ifEmpty { "غير محددة" }}"
        binding.genderTextView.text = "الجنس: ${user.gender.ifEmpty { "غير محدد" }}"

        // عرض الرتبة ولونها
        val userRank = try {
            UserRank.valueOf(user.rank)
        } catch (e: Exception) {
            UserRank.NEWBIE
        }

        val rankText = "الرتبة: ${userRank.displayName}"
        binding.rankTextView.text = rankText

        try {
            val color = Color.parseColor(userRank.color)
            binding.rankTextView.setTextColor(color)
        } catch (e: Exception) {
            binding.rankTextView.setTextColor(Color.GRAY)
        }

        // عرض علم البلد (لاحقًا ممكن نحط صورة حسب رمز الدولة)
        binding.flagEmojiTextView.text = getFlagEmoji(user.countryCode)
    }

    private fun getFlagEmoji(countryCode: String?): String {
        if (countryCode.isNullOrEmpty()) return "🏳️"
        return countryCode.uppercase()
            .map { char -> Character.toChars(0x1F1E6 - 'A'.code + char.code).concatToString() }
            .joinToString("")
    }
}
