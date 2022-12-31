package com.galib.natorepbs2.ui

import com.galib.natorepbs2.utils.Utility.Companion.openPlayStore
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.galib.natorepbs2.R
import com.galib.natorepbs2.constants.URLs
import com.galib.natorepbs2.databinding.FragmentRelatedAppsBinding

class RelatedAppsFragment : Fragment(), MenuOnClickListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DataBindingUtil.inflate<FragmentRelatedAppsBinding>(
            inflater,
            R.layout.fragment_related_apps,
            container,
            false
        )
        binding.pageTitle = getString(R.string.menu_related_apps)
        binding.adapter = MenuAdapter(requireContext(),this, getMenuList())
        binding.lifecycleOwner = activity
        return binding.root
    }

    private fun getMenuList(): MutableList<String> {
        val list : MutableList<String> = ArrayList()
        list.add(getString(R.string.menu_digital_phonebook))
        list.add(getString(R.string.menu_nothi))
        return list
    }

    override fun menuOnClick(menuText: String) {
        when(menuText){
            getString(R.string.menu_digital_phonebook) -> openPlayStore(requireContext(), URLs.DIGITAL_PHONEBOOK_APP_ID)
            getString(R.string.menu_nothi) -> openPlayStore(requireContext(), URLs.NOTHI_APP_ID)
        }
    }
}