package com.galib.natorepbs2.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.galib.natorepbs2.R
import com.galib.natorepbs2.constants.URLs
import com.galib.natorepbs2.databinding.FragmentOurServicesBinding
import com.galib.natorepbs2.utils.Utility

class OurServicesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DataBindingUtil.inflate<FragmentOurServicesBinding>(
            inflater,
            R.layout.fragment_our_services,
            container,
            false
        )
        binding.pageTitle = getString(R.string.menu_our_services)
        binding.electricityConnection = getString(R.string.menu_electricity_connection)
        binding.electricityBill = getString(R.string.menu_electricity_bill)
        binding.serviceList = getString(R.string.menu_service_list)
        binding.citizenCharter = getString(R.string.menu_citizen_charter)
        binding.fragment = this
        binding.lifecycleOwner = activity
        return binding.root
    }

    fun onClick(v: View) {
        when (v.id) {
            R.id.electricity_connection_btn -> {
                findNavController().navigate(R.id.action_ourServicesFragment_to_electricityConnectionFragment)
            }
            R.id.electricity_bill_btn -> {
                findNavController().navigate(R.id.action_ourServicesFragment_to_electricityBillFragment)
            }
            R.id.serviceListBtn -> {
                val action = OurServicesFragmentDirections.actionOurServicesFragmentToWebViewFragment(
                    getString(R.string.menu_service_list),
                    null, Utility.getHowToGetServiceHtml(requireContext().assets), null
                )
                findNavController().navigate(action)
            }
            R.id.citizen_charter_btn -> {
                val action = OurServicesFragmentDirections.actionOurServicesFragmentToPDFViewerFragment(
                    getString(R.string.menu_citizen_charter),
                    URLs.CITIZEN_CHARTER,
                    getString(R.string.menu_citizen_charter)
                )
                findNavController().navigate(action)
            }
        }
    }
}