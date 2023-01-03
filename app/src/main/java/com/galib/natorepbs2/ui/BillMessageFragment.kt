package com.galib.natorepbs2.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.galib.natorepbs2.R
import com.galib.natorepbs2.databinding.FragmentBillMessageBinding

class BillMessageFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DataBindingUtil.inflate<FragmentBillMessageBinding>(inflater, R.layout.fragment_bill_message, container, false)
        binding.pageTitle = getString(R.string.menu_sms_electricity_bill)
        binding.smsBillTitle = getString(R.string.sms_bill_title)
        binding.smsBillDesc = getString(R.string.sms_bill_desc)
        binding.smsBillSample = getString(R.string.sms_bill_sample)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }
}