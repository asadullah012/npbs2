package com.galib.natorepbs2.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.galib.natorepbs2.R
import com.galib.natorepbs2.databinding.FragmentSettingsBinding
import com.galib.natorepbs2.settings.Setting

class SettingsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DataBindingUtil.inflate<FragmentSettingsBinding>(
            inflater,
            R.layout.fragment_settings,
            container,
            false
        )
        binding.s1 = Setting(getString(R.string.menu_favorites), AppCompatResources.getDrawable(requireContext(),R.drawable.ic_favorite_menu)) {
            Log.d("Settings", "onCreateView: Call button clicked!")
            findNavController().navigate(R.id.favMenuSettingsFragment)
        }
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }
}