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
import com.galib.natorepbs2.databinding.FragmentOpinionComplainBinding

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
            getString(R.string.menu_complain_google_form) -> action = OpinionComplainFragmentDirections.actionOpinionComplainFragmentToWebViewFragment(getString(R.string.menu_complain_google_form), URLs.COMPLAIN_GOOGLE_FORM, null, null)
            getString(R.string.menu_grs) -> action = OpinionComplainFragmentDirections.actionOpinionComplainFragmentToWebViewFragment(getString(R.string.menu_grs), URLs.GRS, null, null)
        }
        if(action != null)
            findNavController().navigate(action)
    }
}