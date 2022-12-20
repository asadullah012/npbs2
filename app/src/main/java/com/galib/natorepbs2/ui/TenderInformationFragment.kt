package com.galib.natorepbs2.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.galib.natorepbs2.NPBS2Application
import com.galib.natorepbs2.R
import com.galib.natorepbs2.databinding.FragmentTenderInformationBinding
import com.galib.natorepbs2.db.NoticeInformation
import com.galib.natorepbs2.viewmodel.NoticeInformationViewModel
import com.galib.natorepbs2.viewmodel.NoticeViewModelFactory

class TenderInformationFragment : Fragment() {

    private val tenderInformationViewModel: NoticeInformationViewModel by viewModels {
        NoticeViewModelFactory((activity?.application as NPBS2Application).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentTenderInformationBinding>(inflater, R.layout.fragment_tender_information, container, false)
        binding.pageTitle = getString(R.string.menu_tender)
        binding.lifecycleOwner = activity
        val adapter = TenderInformationAdapter()
        tenderInformationViewModel.getAllTender()?.observe(viewLifecycleOwner) { allTender ->
            allTender?.let { adapter.differ.submitList(it) }
        }
        binding.adapter = adapter
        return binding.root
    }

}