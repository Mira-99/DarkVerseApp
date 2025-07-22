package com.darkverse.app.ranks

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.darkverse.app.databinding.ItemAchievementBinding
import com.darkverse.app.models.Achievement

class AchievementsAdapter(
    private val achievements: List<Achievement>
) : RecyclerView.Adapter<AchievementsAdapter.AchievementViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AchievementViewHolder {
        val binding = ItemAchievementBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AchievementViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AchievementViewHolder, position: Int) {
        holder.bind(achievements[position])
    }

    override fun getItemCount(): Int = achievements.size

    inner class AchievementViewHolder(private val binding: ItemAchievementBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(achievement: Achievement) {
            binding.achievementTitle.text = achievement.title
            binding.achievementDescription.text = achievement.description
            binding.achievementCategory.text = achievement.category.displayName
            binding.achievementRarity.text = achievement.rarity.displayName
            
            // Set rarity color
            binding.achievementRarity.setTextColor(Color.parseColor(achievement.rarity.color))
            
            // Set unlock status
            if (achievement.isUnlocked) {
                binding.achievementStatus.text = "مفتوح"
                binding.root.alpha = 1.0f
            } else {
                binding.achievementStatus.text = "مقفل"
                binding.root.alpha = 0.5f
            }
        }
    }
}

