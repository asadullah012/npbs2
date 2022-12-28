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
import com.galib.natorepbs2.databinding.FragmentWebsitesBinding

class WebsitesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DataBindingUtil.inflate<FragmentWebsitesBinding>(
            inflater,
            R.layout.fragment_websites,
            container,
            false
        )
        binding.pageTitle = getString(R.string.menu_websites)
        binding.websiteBreb = getString(R.string.menu_breb)
        binding.websiteNatorePbs2 = getString(R.string.menu_natore_pbs2)
        binding.fragment = this
        binding.lifecycleOwner = activity
        return binding.root
    }

    fun onClick(v: View) {
        when (v.id) {
            R.id.breb_btn -> {
                val action = WebsitesFragmentDirections.actionWebsitesFragmentToWebViewFragment(getString(R.string.menu_breb), URLs.BREB, null, null)
                findNavController().navigate(action)
            }
            R.id.natore_pbs2_btn -> {
                val action = WebsitesFragmentDirections.actionWebsitesFragmentToWebViewFragment(getString(R.string.menu_natore_pbs2), URLs.NATORE_PBS_2, null, null)
                findNavController().navigate(action)
            }
        }
    }
}