package com.galib.natorepbs2.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.galib.natorepbs2.NPBS2Application
import com.galib.natorepbs2.R
import com.galib.natorepbs2.viewmodel.AchievementViewModel
import com.galib.natorepbs2.viewmodel.AchievementViewModelFactory

class AchievementActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_achievement)
        val achievementViewModel: AchievementViewModel by viewModels {
            AchievementViewModelFactory((application as NPBS2Application).repository)
        }
        val recyclerView = findViewById<RecyclerView>(R.id.achievementRecyclerView)
        val adapter = AchievementAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        achievementViewModel.allAchievement.observe(this) { allAchievement ->
            allAchievement?.let { adapter.submitList(it) }
        }
    }
}