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

class RelatedAppsFragment : Fragment() {

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
        binding.digitalPhonebook = getString(R.string.menu_digital_phonebook)
        binding.nothi = getString(R.string.menu_nothi)
        binding.fragment = this
        binding.lifecycleOwner = activity
        return binding.root
    }

    fun onClick(v: View) {
        when (v.id) {
            R.id.digital_phonebook_btn -> {
                openPlayStore(requireContext(), URLs.DIGITAL_PHONEBOOK_APP_ID)
            }
            R.id.nothi_btn -> {
                openPlayStore(requireContext(), URLs.NOTHI_APP_ID)
            }
        }
    }
}