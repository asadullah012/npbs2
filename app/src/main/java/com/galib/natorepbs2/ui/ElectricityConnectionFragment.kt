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
import com.galib.natorepbs2.constants.URLs
import com.galib.natorepbs2.databinding.FragmentElectricityConnectionBinding
import com.galib.natorepbs2.utils.Utility

class ElectricityConnectionFragment : Fragment(), MenuOnClickListener {

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
        binding.adapter = MenuAdapter(requireContext(),this, getMenuList())
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }


    private fun getMenuList(): MutableList<String> {
        val list : MutableList<String> = ArrayList()
        list.add(getString(R.string.menu_electricity_connection_rules))
        list.add(getString(R.string.menu_connection_domestic))
        list.add(getString(R.string.menu_connection_domestic_new))
        list.add(getString(R.string.menu_connection_industry))
        list.add(getString(R.string.menu_sms_connection))
        return list
    }

    override fun menuOnClick(menuText: String) {
        var action:NavDirections? = null
        when(menuText){
            getString(R.string.menu_electricity_connection_rules) -> action = ElectricityConnectionFragmentDirections.actionElectricityConnectionFragmentToWebViewFragment(getString(R.string.menu_electricity_connection_rules), null, Utility.getConnectionRulesHtml(requireContext().assets), null)
            getString(R.string.menu_connection_domestic) -> action = ElectricityConnectionFragmentDirections.actionElectricityConnectionFragmentToWebViewFragment(getString(R.string.menu_connection_domestic), URLs.CONNECTION_DOMESTIC, null, null)
            getString(R.string.menu_connection_domestic_new) -> action = ElectricityConnectionFragmentDirections.actionElectricityConnectionFragmentToWebViewFragment(getString(R.string.menu_connection_domestic_new), URLs.CONNECTION_DOMESTIC_NEW, null, null)
            getString(R.string.menu_connection_industry) -> action = ElectricityConnectionFragmentDirections.actionElectricityConnectionFragmentToWebViewFragment(getString(R.string.menu_connection_domestic), URLs.CONNECTION_INDUSTRY, null, null)
            getString(R.string.menu_sms_connection) -> action = ElectricityConnectionFragmentDirections.actionElectricityConnectionFragmentToConnectionMessageFragment()
        }
        if(action != null)
            findNavController().navigate(action)
    }
}