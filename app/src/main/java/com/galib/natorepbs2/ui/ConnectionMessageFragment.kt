package com.galib.natorepbs2.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.galib.natorepbs2.R
import com.galib.natorepbs2.databinding.FragmentConnectionMessageBinding

class ConnectionMessageFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DataBindingUtil.inflate<FragmentConnectionMessageBinding>(inflater, R.layout.fragment_connection_message, container, false)
        binding.pageTitle = getString(R.string.menu_sms_connection)
        binding.smsNewConnectionCompleteTitle = getString(R.string.sms_new_connection_complete_title)
        binding.smsNewConnectionCompleteDesc = getString(R.string.sms_new_connection_complete_desc)
        binding.smsNewConnectionCompleteSample = getString(R.string.sms_new_connection_complete_sample)
        binding.smsApplicationDepositeTitle = getString(R.string.sms_application_deposite_title)
        binding.smsApplicationDepositeDesc = getString(R.string.sms_application_deposite_desc)
        binding.smsApplicationDepositeSample = getString(R.string.sms_application_deposite_sample)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

}