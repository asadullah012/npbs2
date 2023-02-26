package com.galib.natorepbs2.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.galib.natorepbs2.R
import com.galib.natorepbs2.databinding.FragmentOurServicesBinding
import com.galib.natorepbs2.sync.SyncConfig
import com.galib.natorepbs2.utils.Utility

class OurServicesFragment : Fragment(), MenuOnClickListener {

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
        binding.adapter = MenuAdapter(requireContext(),this, getMenuList())
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }
    private fun getMenuList(): MutableList<String> {
        val list : MutableList<String> = ArrayList()
        list.add(getString(R.string.menu_electricity_connection))
        list.add(getString(R.string.menu_service_list))
        list.add(getString(R.string.menu_citizen_charter))
        return list
    }

    override fun menuOnClick(menuText: String) {
        when (menuText){
            getString(R.string.menu_electricity_connection) -> findNavController().navigate(R.id.action_ourServicesFragment_to_electricityConnectionFragment)
            getString(R.string.menu_service_list) -> {
                val action = OurServicesFragmentDirections.actionOurServicesFragmentToWebViewFragment(
                    getString(R.string.menu_service_list),
                    null, Utility.getHowToGetServiceHtml(requireContext().assets), null
                )
                findNavController().navigate(action)
            }
            getString(R.string.menu_citizen_charter) ->{
                val action = OurServicesFragmentDirections.actionOurServicesFragmentToPDFViewerFragment(
                    getString(R.string.menu_citizen_charter),
                    SyncConfig.getUrl("CITIZEN_CHARTER", requireContext()),
                    getString(R.string.menu_citizen_charter)
                )
                findNavController().navigate(action)
            }
        }
    }

}