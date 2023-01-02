package com.galib.natorepbs2.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.galib.natorepbs2.NPBS2Application
import com.galib.natorepbs2.R
import com.galib.natorepbs2.databinding.FragmentSettingsBinding
import com.galib.natorepbs2.models.MyMenuItem
import com.google.android.flexbox.*

class SettingsFragment : Fragment(), SettingsOnClickListener  {
    lateinit var favoriteMenuList: MutableLiveData<List<MyMenuItem>>
    lateinit var availableMenuList: MutableLiveData<List<MyMenuItem>>
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
        favoriteMenuList = (activity?.application as NPBS2Application).repository.favoriteMenuList
        availableMenuList = (activity?.application as NPBS2Application).repository.availableMenuList

        Log.d("TAG", "onCreateView: ${favoriteMenuList.value?.size}")
        Log.d("TAG", "onCreateView: ${availableMenuList.value?.size}")

        val favoriteMenuAdapter = SettingsAdapter(this, true)
        val availableMenuAdapter = SettingsAdapter(this, false)
        binding.favoriteMenuAdapter = favoriteMenuAdapter
        binding.availableMenuAdapter = availableMenuAdapter

        favoriteMenuList.observe(viewLifecycleOwner) { menuList ->
            menuList?.let { favoriteMenuAdapter.submitList(it) }
        }
        availableMenuList.observe(viewLifecycleOwner) { menuList ->
            menuList?.let { availableMenuAdapter.submitList(it) }
        }
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun settingsOnClick(index: Int, isFavoriteAdapter: Boolean) {
        if(isFavoriteAdapter){
            val removed = favoriteMenuList.value?.toMutableList()?.get(index)
            favoriteMenuList.value = favoriteMenuList.value?.toMutableList()?.apply {
                removeAt(index)
            }?.toList()
            availableMenuList.value = availableMenuList.value?.plus(removed!!)
        } else {
            val removed = availableMenuList.value?.toMutableList()?.get(index)
            availableMenuList.value = availableMenuList.value?.toMutableList()?.apply {
                removeAt(index)
            }?.toList()
            favoriteMenuList.value = favoriteMenuList.value?.plus(removed!!)
        }
    }

}