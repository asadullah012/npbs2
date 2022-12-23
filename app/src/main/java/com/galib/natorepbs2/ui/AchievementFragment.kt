package com.galib.natorepbs2.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.galib.natorepbs2.NPBS2Application
import com.galib.natorepbs2.R
import com.galib.natorepbs2.viewmodel.AchievementViewModel
import com.galib.natorepbs2.viewmodel.AchievementViewModelFactory

class AchievementFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val achievementViewModel: AchievementViewModel by viewModels {
            AchievementViewModelFactory((activity?.application as NPBS2Application).repository)
        }
        val root = inflater.inflate(R.layout.fragment_achievement, container, false)
        val recyclerView = root.findViewById<RecyclerView>(R.id.achievementRecyclerView)
        val adapter = AchievementAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        achievementViewModel.allAchievement.observe(viewLifecycleOwner) { allAchievement ->
            allAchievement?.let { adapter.submitList(it) }
        }
        return root
    }

   }