package com.darkverse.app.chat

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.darkverse.app.databinding.ActivityMatchingBinding
import com.darkverse.app.models.User
import com.darkverse.app.models.UserRank
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MatchingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMatchingBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMatchingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        binding.findMatchButton.setOnClickListener {
            findMatch()
        }
    }

    private fun findMatch() {
        binding.findMatchButton.isEnabled = false
        binding.findMatchButton.text = "جاري البحث..."
        binding.matchedUserTextView.text = "البحث عن شريك مناسب..."

        val currentUserId = auth.currentUser?.uid
        if (currentUserId == null) {
            Toast.makeText(this, "يجب تسجيل الدخول أولاً", Toast.LENGTH_SHORT).show()
            binding.findMatchButton.isEnabled = true
            binding.findMatchButton.text = "ابحث عن شريك الآن"
            return
        }

        database.reference.child("users").child(currentUserId).get()
            .addOnSuccessListener { currentUserSnapshot ->
                val currentUser = currentUserSnapshot.getValue(User::class.java)
                if (currentUser != null) {
                    searchForCompatibleUser(currentUser)
                } else {
                    showError("فشل في تحميل بيانات المستخدم")
                }
            }
            .addOnFailureListener {
                showError("فشل في الاتصال بقاعدة البيانات")
            }
    }

    private fun searchForCompatibleUser(currentUser: User) {
        database.reference.child("users")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val potentialMatches = mutableListOf<User>()

                    val currentUserRank = UserRank.fromName(currentUser.rank)

                    for (userSnapshot in snapshot.children) {
                        val user = userSnapshot.getValue(User::class.java)
                        if (user != null && user.uid != currentUser.uid && user.isOnline) {
                            val userRank = UserRank.fromName(user.rank)
                            val compatibility = calculateCompatibility(currentUser, currentUserRank, user, userRank)
                            if (compatibility > 0.5) {
                                potentialMatches.add(user)
                            }
                        }
                    }

                    if (potentialMatches.isNotEmpty()) {
                        val matchedUser = potentialMatches.random()
                        showMatch(matchedUser)
                    } else {
                        showNoMatch()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    showError("فشل في البحث: ${error.message}")
                }
            })
    }

    private fun calculateCompatibility(user1: User, rank1: UserRank, user2: User, rank2: UserRank): Double {
        var score = 0.0

        val commonAnime = user1.favoriteAnime.intersect(user2.favoriteAnime.toSet())
        score += commonAnime.size * 0.3

        val rankDifference = kotlin.math.abs(rank1.minLevel - rank2.minLevel)
        score += if (rankDifference <= 10) 0.4 else 0.1

        val levelDifference = kotlin.math.abs(user1.level - user2.level)
        score += if (levelDifference <= 5) 0.3 else 0.1

        return score
    }

    private fun showMatch(matchedUser: User) {
        binding.findMatchButton.isEnabled = true
        binding.findMatchButton.text = "ابحث عن شريك آخر"

        val matchedUserRank = UserRank.fromName(matchedUser.rank)
        binding.matchedUserTextView.text = "تم العثور على شريك: ${matchedUser.displayName}\nالرتبة: ${matchedUserRank.displayName}"

        Toast.makeText(this, "تم العثور على شريك متوافق!", Toast.LENGTH_SHORT).show()
    }

    private fun showNoMatch() {
        binding.findMatchButton.isEnabled = true
        binding.findMatchButton.text = "ابحث عن شريك الآن"
        binding.matchedUserTextView.text = "لم يتم العثور على شريك متوافق حالياً. حاول مرة أخرى لاحقاً."
    }

    private fun showError(message: String) {
        binding.findMatchButton.isEnabled = true
        binding.findMatchButton.text = "ابحث عن شريك الآن"
        binding.matchedUserTextView.text = ""
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
