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
import com.galib.natorepbs2.databinding.FragmentFavMenuSettingsBinding
import com.galib.natorepbs2.viewmodel.SettingsViewModel
import com.galib.natorepbs2.viewmodel.SettingsViewModelFactory

class FavMenuSettingsFragment : Fragment(), SettingsOnClickListener  {
    private val settingsViewModel: SettingsViewModel by viewModels {
        SettingsViewModelFactory((activity?.application as NPBS2Application).repository)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DataBindingUtil.inflate<FragmentFavMenuSettingsBinding>(
            inflater,
            R.layout.fragment_fav_menu_settings,
            container,
            false
        )
        val favoriteMenuAdapter = SettingsAdapter(this)

        settingsViewModel.allMenu.observe(viewLifecycleOwner) { menuList ->
            menuList?.let { favoriteMenuAdapter.submitList(it) }
        }
        binding.favoriteMenuAdapter = favoriteMenuAdapter
        binding.pageTitle = getString(R.string.menu_settings_favorite_menu)
        binding.choseFavoriteMenu = getString(R.string.menu_settings_choose_favorite_menu)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun settingsOnClick(name:String, isFavoriteAdapter: Boolean) {
        settingsViewModel.updateMyMenu(name, isFavoriteAdapter )
    }
}