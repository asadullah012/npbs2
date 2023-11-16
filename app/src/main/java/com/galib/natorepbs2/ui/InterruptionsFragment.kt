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
import com.galib.natorepbs2.databinding.FragmentViewInterruptionsBinding
import com.galib.natorepbs2.logger.LogUtils
import com.galib.natorepbs2.viewmodel.InterruptionsViewModel
import com.galib.natorepbs2.viewmodel.InterruptionsViewModelFactory

class InterruptionsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val interruptionViewModel: InterruptionsViewModel by viewModels {
            InterruptionsViewModelFactory((activity?.application as NPBS2Application).repository)
        }
        val binding = DataBindingUtil.inflate<FragmentViewInterruptionsBinding>(inflater, R.layout.fragment_view_interruptions, container, false)
        binding.viewModel = interruptionViewModel
        binding.refreshButton.setOnClickListener {
            LogUtils.d("TAG", "onRefreshClicked: On refresh clicked")
            interruptionViewModel.onRefreshClicked()
        }
        val adapter = InterruptionsAdapter(requireContext())
        interruptionViewModel.allInteruption.observe(viewLifecycleOwner) { list -> adapter.submitList(list) }
        binding.adapter = adapter
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }
}