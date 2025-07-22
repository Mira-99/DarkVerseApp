package com.darkverse.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.darkverse.app.databinding.ActivityMatchingBinding
import kotlin.math.abs

data class User(
    val displayName: String,
    val rank: String // هذا يبقى String
)

class MatchingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMatchingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMatchingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val currentUser = User("ميران", "شيطان مبتدئ")
        val otherUsers = listOf(
            User("ريما", "سيد الظلال"),
            User("أكرم", "شيطان مبتدئ"),
            User("لارا", "متجسد الظلام")
        )

        val matchedUser = findBestMatch(currentUser, otherUsers)
        showMatch(matchedUser)
    }

    private fun findBestMatch(currentUser: User, otherUsers: List<User>): User {
        return otherUsers.minByOrNull { otherUser ->
            calculateCompatibility(currentUser, otherUser)
        } ?: otherUsers.first()
    }

    private fun calculateCompatibility(user1: User, user2: User): Int {
        val rank1 = UserRank.fromName(user1.rank)
        val rank2 = UserRank.fromName(user2.rank)
        val rankDifference = abs(rank1.minLevel - rank2.minLevel)
        return rankDifference
    }

    private fun showMatch(matchedUser: User) {
        val matchedRank = UserRank.fromName(matchedUser.rank)
        binding.matchedUserTextView.text = "تم العثور على شريك: ${matchedUser.displayName}\nالرتبة: ${matchedRank.displayName}"
    }
}
