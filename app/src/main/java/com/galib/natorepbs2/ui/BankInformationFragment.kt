package com.galib.natorepbs2.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.galib.natorepbs2.R
import com.galib.natorepbs2.constants.Category
import com.galib.natorepbs2.databinding.FragmentBankInformationBinding
import com.galib.natorepbs2.db.Information
import com.galib.natorepbs2.viewmodel.InformationViewModel

class BankInformationFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding =
            DataBindingUtil.inflate<FragmentBankInformationBinding>(inflater, R.layout.fragment_bank_information, container, false)
        binding.pageTitle = getString(R.string.menu_at_a_glance)
        binding.lifecycleOwner = activity
        val informationViewModel = ViewModelProvider(requireActivity())[InformationViewModel::class.java]
        val adapter = InformationAdapter(InformationAdapter.WordDiff())
        informationViewModel.getInformationByCategory(Category.bank).observe(viewLifecycleOwner) { list: List<Information?> -> adapter.submitList(list) }
        binding.adapter = adapter
        return binding.root
    }
}