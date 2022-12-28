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
import com.galib.natorepbs2.databinding.FragmentElectricityConnectionBinding
import com.galib.natorepbs2.utils.Utility

class ElectricityConnectionFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DataBindingUtil.inflate<FragmentElectricityConnectionBinding>(
            inflater,
            R.layout.fragment_electricity_connection,
            container,
            false
        )
        binding.pageTitle = getString(R.string.menu_electricity_connection)
        binding.connectionRules = getString(R.string.menu_electricity_connection_rules)
        binding.connectionDomestic = getString(R.string.menu_connection_domestic)
        binding.connectionIndustry = getString(R.string.menu_connection_industry)
        binding.connectionSms = getString(R.string.menu_sms_connection)
        binding.fragment = this
        binding.lifecycleOwner = activity
        return binding.root
    }

    fun onClick(v: View) {
        when (v.id) {
            R.id.electricity_connection_rules_btn -> {
                val action =
                    ElectricityConnectionFragmentDirections.actionElectricityConnectionFragmentToWebViewFragment(
                        getString(R.string.menu_electricity_connection_rules),
                        null, Utility.getConnectionRulesHtml(requireContext().assets), null
                    )
                findNavController().navigate(action)
            }
            R.id.connection_domestic_btn -> {
                val action =
                    ElectricityConnectionFragmentDirections.actionElectricityConnectionFragmentToWebViewFragment(
                        getString(R.string.menu_connection_domestic),
                        URLs.CONNECTOION_DOMESTIC,
                        null,
                        null
                    )
                findNavController().navigate(action)
            }
            R.id.connection_industry_btn -> {
                val action =
                    ElectricityConnectionFragmentDirections.actionElectricityConnectionFragmentToWebViewFragment(
                        getString(R.string.menu_connection_domestic),
                        URLs.CONNECTION_INDUSTRY,
                        null,
                        null
                    )
                findNavController().navigate(action)
            }
            R.id.connection_sms_btn -> {
                findNavController().navigate(R.id.action_electricityConnectionFragment_to_connectionMessageFragment)
            }
        }
    }
}