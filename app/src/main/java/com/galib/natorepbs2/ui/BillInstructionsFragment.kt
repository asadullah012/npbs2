package com.galib.natorepbs2.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.galib.natorepbs2.NPBS2Application
import com.galib.natorepbs2.R
import com.galib.natorepbs2.databinding.FragmentBillInstructionsBinding
import com.galib.natorepbs2.viewmodel.InformationViewModel
import com.galib.natorepbs2.viewmodel.InformationViewModelFactory

class BillInstructionsFragment : Fragment() {
    private val args: BillInstructionsFragmentArgs by navArgs()
    private val informationViewModel: InformationViewModel by viewModels {
        InformationViewModelFactory((activity?.application as NPBS2Application).repository)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentBillInstructionsBinding>(inflater, R.layout.fragment_bill_instructions, container, false)
        binding.pageTitle = args.title
        binding.lifecycleOwner = activity
        val adapter = InstructionAdapter(requireContext())
        val type = args.billType
        adapter.submitList(informationViewModel.getInstructionByType(type))
        binding.adapter = adapter
        return binding.root
    }
}