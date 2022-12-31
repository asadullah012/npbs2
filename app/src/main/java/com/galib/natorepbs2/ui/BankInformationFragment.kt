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
import com.galib.natorepbs2.constants.Category
import com.galib.natorepbs2.databinding.FragmentBankInformationBinding
import com.galib.natorepbs2.models.Information
import com.galib.natorepbs2.viewmodel.InformationViewModel
import com.galib.natorepbs2.viewmodel.InformationViewModelFactory

class BankInformationFragment : Fragment() {
    private val informationViewModel: InformationViewModel by viewModels {
        InformationViewModelFactory((activity?.application as NPBS2Application).repository)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding =
            DataBindingUtil.inflate<FragmentBankInformationBinding>(inflater, R.layout.fragment_bank_information, container, false)
        binding.pageTitle = getString(R.string.menu_bill_collection_bank)
        binding.lifecycleOwner = activity
        val adapter = InformationAdapter()
        informationViewModel.getInformationByCategory(Category.bank).observe(viewLifecycleOwner) { list: List<Information?> -> adapter.submitList(list) }
        binding.adapter = adapter
        return binding.root
    }
}