package com.galib.natorepbs2.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.galib.natorepbs2.R
import com.galib.natorepbs2.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment()  {
    lateinit var rootView: View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentSettingsBinding>(
            inflater,
            R.layout.fragment_settings,
            container,
            false
        )
        binding.recyclerView1.init(listOf("A", "B", "C"), binding.emptyListTextView1)
        binding.recyclerView2.init(listOf("1", "2", "3"), binding.emptyListTextView2)
        rootView = binding.root
        return binding.root
    }

    private fun RecyclerView.init(list: List<String>, emptyTextView: TextView) {
        this.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val adapter = CustomAdapter(list, object :CustomListener{
            override fun setEmptyList(visibility: Int, recyclerView: Int, emptyTextView: Int) {
                rootView.findViewById<RecyclerView>(recyclerView).visibility = visibility
                rootView.findViewById<TextView>(emptyTextView).visibility = visibility
            }
        })
        this.adapter = adapter
        emptyTextView.setOnDragListener(adapter.dragInstance)
        this.setOnDragListener(adapter.dragInstance)
    }


}