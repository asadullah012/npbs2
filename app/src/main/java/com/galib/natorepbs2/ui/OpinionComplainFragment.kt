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
import com.galib.natorepbs2.databinding.FragmentOpinionComplainBinding
import com.galib.natorepbs2.sync.SyncConfig

class OpinionComplainFragment : Fragment(), MenuOnClickListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DataBindingUtil.inflate<FragmentOpinionComplainBinding>(
            inflater,
            R.layout.fragment_opinion_complain,
            container,
            false
        )
        binding.pageTitle = getString(R.string.menu_opinion_complain)
        binding.adapter = MenuAdapter(requireContext(),this, getMenuList())
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    private fun getMenuList(): MutableList<String> {
        val list : MutableList<String> = ArrayList()
        list.add(getString(R.string.menu_complain_google_form))
        list.add(getString(R.string.menu_grs))
        return list
    }

    override fun menuOnClick(menuText: String) {
        var action: NavDirections? = null
        when (menuText) {
            getString(R.string.menu_complain_google_form) -> action = OpinionComplainFragmentDirections.actionOpinionComplainFragmentToWebViewFragment(getString(R.string.menu_complain_google_form), SyncConfig.getUrl("COMPLAIN_GOOGLE_FORM", requireContext()), null, null)
            getString(R.string.menu_grs) -> action = OpinionComplainFragmentDirections.actionOpinionComplainFragmentToWebViewFragment(getString(R.string.menu_grs), SyncConfig.getUrl("GRS", requireContext()), null, null)
        }
        if(action != null)
            findNavController().navigate(action)
    }
}