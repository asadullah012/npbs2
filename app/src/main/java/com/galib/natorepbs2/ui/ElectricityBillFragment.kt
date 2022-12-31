package com.galib.natorepbs2.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.galib.natorepbs2.R
import com.galib.natorepbs2.databinding.FragmentElectricityBillBinding
import com.galib.natorepbs2.utils.Utility

class ElectricityBillFragment : Fragment(),MenuOnClickListener {

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
        binding.adapter = MenuAdapter(requireContext(),this, getMenuList())
        binding.lifecycleOwner = activity
        return binding.root
    }

    fun getMenuList():MutableList<String>{
        val list : MutableList<String> = ArrayList()
        list.add(getString(R.string.menu_bill_collection_bank))
        list.add(getString(R.string.menu_bill_from_home))
        list.add(getString(R.string.menu_electricity_tariff))
        list.add(getString(R.string.menu_electricity_bill_calculator))
        list.add(getString(R.string.menu_sms_electricity_bill))
        return list
    }

    override fun menuOnClick(menuText: String) {
        var action: NavDirections? = null
        when(menuText){
            getString(R.string.menu_bill_collection_bank) -> action = ElectricityBillFragmentDirections.actionElectricityBillFragmentToBankInformationFragment()
            getString(R.string.menu_bill_from_home) -> action = ElectricityBillFragmentDirections.actionElectricityBillFragmentToBillFromHomeFragment()
            getString(R.string.menu_electricity_tariff) -> action = ElectricityBillFragmentDirections.actionElectricityBillFragmentToWebViewFragment(getString(R.string.menu_electricity_tariff), null, Utility.getTariffHtml(requireContext().assets), null)
            getString(R.string.menu_electricity_bill_calculator) -> action = ElectricityBillFragmentDirections.actionElectricityBillFragmentToBillCalculatorFragment()
            getString(R.string.menu_sms_electricity_bill) -> action = ElectricityBillFragmentDirections.actionElectricityBillFragmentToBillMessageFragment()
        }
        if(action != null)
            findNavController().navigate(action)
    }
}