package com.galib.natorepbs2.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.galib.natorepbs2.NPBS2Application
import com.galib.natorepbs2.R
import com.galib.natorepbs2.databinding.FragmentComplainCentreBinding
import com.galib.natorepbs2.viewmodel.ComplainCentreViewModel
import com.galib.natorepbs2.viewmodel.ComplainCentreViewModelFactory

class ComplainCentreFragment : Fragment() {
    private val complainCentreViewModel: ComplainCentreViewModel by viewModels {
        ComplainCentreViewModelFactory((activity?.application as NPBS2Application).repository)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DataBindingUtil.inflate<FragmentComplainCentreBinding>(inflater, R.layout.fragment_complain_centre, container, false)
        binding.pageTitle = getString(R.string.menu_complain_centres)
        binding.lifecycleOwner = activity
        val adapter = ComplainCentreAdapter(requireContext())
        complainCentreViewModel.allComplainCentre.observe(viewLifecycleOwner) { list -> adapter.submitList(list) }
        binding.adapter = adapter
        return binding.root
    }

}