package com.galib.natorepbs2.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.galib.natorepbs2.NPBS2Application
import com.galib.natorepbs2.R
import com.galib.natorepbs2.viewmodel.ComplainCentreViewModel
import com.galib.natorepbs2.viewmodel.ComplainCentreViewModelFactory

class ComplainCentreActivity : AppCompatActivity() {
    private val complainCentreViewModel: ComplainCentreViewModel by viewModels {
        ComplainCentreViewModelFactory((application as NPBS2Application).repository)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_complain_centre)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = ComplainCentreAdapter(this, ComplainCentreAdapter.WordDiff())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        complainCentreViewModel.allComplainCentre.observe(this) { list ->
            adapter.submitList(
                list
            )
        }
    }
}