package com.galib.natorepbs2.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.galib.natorepbs2.R
import com.galib.natorepbs2.databinding.FragmentElectricityBillBinding
import com.galib.natorepbs2.utils.Utility

class ElectricityBillFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DataBindingUtil.inflate<FragmentElectricityBillBinding>(
            inflater,
            R.layout.fragment_electricity_bill,
            container,
            false
        )
        binding.pageTitle = getString(R.string.menu_electricity_bill)
        binding.electricityTariff = getString(R.string.menu_electricity_tariff)
        binding.smsBill = getString(R.string.menu_sms_electricity_bill)
        binding.billCollectionBank = getString(R.string.menu_bill_collection_bank)
        binding.billFromHome = getString(R.string.menu_bill_from_home)
        binding.billCalculator = getString(R.string.menu_electricity_bill_calculator)
        binding.fragment = this
        binding.lifecycleOwner = activity
        return binding.root
    }

    fun onClick(v: View) {
        when (v.id) {
            R.id.bill_from_home_btn -> {
                findNavController().navigate(R.id.action_electricityBillFragment_to_billFromHomeFragment)
            }
            R.id.electricity_tarriff_btn -> {
                val html = Utility.getTariffHtml(requireContext().assets)
                val action =
                    ElectricityBillFragmentDirections.actionElectricityBillFragmentToWebViewFragment(
                        getString(R.string.menu_electricity_tariff),
                        null, html, null)
                findNavController().navigate(action)
            }
            R.id.sms_electricity_bill_btn -> {
                findNavController().navigate(R.id.action_electricityBillFragment_to_billMessageFragment)
            }
            R.id.bill_collection_bank_btn -> {
                findNavController().navigate(R.id.action_electricityBillFragment_to_bankInformationFragment)
            }
            R.id.bill_calculator_btn -> findNavController().navigate(R.id.action_electricityBillFragment_to_billCalculatorFragment)
        }
    }
}