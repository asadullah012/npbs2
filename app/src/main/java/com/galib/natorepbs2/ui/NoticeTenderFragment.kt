package com.galib.natorepbs2.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.galib.natorepbs2.R
import com.galib.natorepbs2.databinding.FragmentNoticeTenderBinding

class NoticeTenderFragment : Fragment(), MenuOnClickListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DataBindingUtil.inflate<FragmentNoticeTenderBinding>(
            inflater,
            R.layout.fragment_notice_tender,
            container,
            false
        )
        binding.pageTitle = getString(R.string.menu_notice_tender)
        binding.adapter = MenuAdapter(requireContext(),this, getMenuList())
        binding.lifecycleOwner = activity
        return binding.root
    }

    private fun getMenuList(): MutableList<String> {
        val list : MutableList<String> = ArrayList()
        list.add(getString(R.string.menu_notice))
        list.add(getString(R.string.menu_tender))
        list.add(getString(R.string.menu_news))
        list.add(getString(R.string.menu_job))
        return list
    }

    override fun menuOnClick(menuText: String) {
        findNavController().navigate(NoticeTenderFragmentDirections.actionNoticeTenderFragmentToNoticeInformationFragment(menuText))
    }
}