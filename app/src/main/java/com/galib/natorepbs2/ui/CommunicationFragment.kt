package com.galib.natorepbs2.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.galib.natorepbs2.R
import com.galib.natorepbs2.databinding.FragmentCommunicationBinding
import com.galib.natorepbs2.utils.Utility
import com.galib.natorepbs2.utils.Utility.Companion.openMap

class CommunicationFragment : Fragment(), MenuOnClickListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentCommunicationBinding>(
            inflater,
            R.layout.fragment_communication,
            container,
            false
        )
        binding.pageTitle = getString(R.string.menu_communication)
        binding.adapter = MenuAdapter(requireContext(),this, getMenuList())
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    fun getMenuList() : MutableList<String>{
        val list : MutableList<String> = ArrayList()
        list.add(getString(R.string.menu_communication_post))
        list.add(getString(R.string.menu_communication_call))
        list.add(getString(R.string.menu_communication_map))
        return list
    }

    override fun menuOnClick(menuText: String) {
        when (menuText) {
            getString(R.string.menu_communication_post) -> findNavController().navigate(CommunicationFragmentDirections.actionCommunicationFragmentToWebViewFragment(getString(R.string.menu_communication_post), null, Utility.getStringFromSyncData(requireContext(), "communication_post"), null))
            getString(R.string.menu_communication_map) -> openMap(requireContext(), "Natore+Palli+Bidyut+Samity-2,+Bonpara,+Natore", 24.295823, 89.0811235)
            getString(R.string.menu_communication_call) -> Utility.makeCall(requireContext(), "01769-404040")
        }
    }
}