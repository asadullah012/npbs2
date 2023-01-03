package com.galib.natorepbs2.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.galib.natorepbs2.NPBS2Application
import com.galib.natorepbs2.R
import com.galib.natorepbs2.databinding.FragmentSettingsBinding
import com.galib.natorepbs2.viewmodel.SettingsViewModel
import com.galib.natorepbs2.viewmodel.SettingsViewModelFactory

class SettingsFragment : Fragment(), SettingsOnClickListener  {
    private val settingsViewModel: SettingsViewModel by viewModels {
        SettingsViewModelFactory((activity?.application as NPBS2Application).repository)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentSettingsBinding>(
            inflater,
            R.layout.fragment_settings,
            container,
            false
        )

        val favoriteMenuAdapter = SettingsAdapter(requireContext(),this, true)
        val availableMenuAdapter = SettingsAdapter(requireContext(), this, false)
        binding.favoriteMenuAdapter = favoriteMenuAdapter
        binding.availableMenuAdapter = availableMenuAdapter

        settingsViewModel.favoriteMenu.observe(viewLifecycleOwner) { menuList ->
            menuList?.let { favoriteMenuAdapter.submitList(it) }
        }
        settingsViewModel.availableMenu.observe(viewLifecycleOwner) { menuList ->
            menuList?.let { availableMenuAdapter.submitList(it) }
        }
        binding.pageTitle = getString(R.string.menu_settings)
        binding.choseFavoriteMenu = getString(R.string.menu_settings_choose_favorite_menu)
        binding.favoriteMenu = getString(R.string.menu_settings_favorite_menu)
        binding.availableMenu = getString(R.string.menu_settings_available_menu)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun settingsOnClick(name:String, isFavoriteAdapter: Boolean) {
        if(isFavoriteAdapter)
            settingsViewModel.removeFromFavoriteMenu(name)
        else
            settingsViewModel.addToFavoriteMenu(name)
    }

}