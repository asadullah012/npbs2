package com.galib.natorepbs2.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.galib.natorepbs2.R
import com.galib.natorepbs2.databinding.FragmentTenderInformationBinding
import com.galib.natorepbs2.db.TenderInformation
import com.galib.natorepbs2.viewmodel.TenderInformationViewModel

class TenderInformationFragment : Fragment() {

    private val tenderInformationViewModel  by lazy { ViewModelProvider(this)[TenderInformationViewModel::class.java] }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentTenderInformationBinding>(inflater, R.layout.fragment_tender_information, container, false)
        binding.pageTitle = getString(R.string.menu_at_a_glance)
        binding.lifecycleOwner = activity
        val adapter = TenderInformationAdapter()
        tenderInformationViewModel.getAllTender()?.observe(viewLifecycleOwner) { list: List<TenderInformation?> ->
            adapter.differ.submitList(list)
        }
        binding.adapter = adapter
        return binding.root
    }

}