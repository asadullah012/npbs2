package com.galib.natorepbs2.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.galib.natorepbs2.R
import com.galib.natorepbs2.databinding.FragmentRelatedAppsBinding
import com.galib.natorepbs2.utils.Utility.Companion.openPlayStore

class RelatedAppsFragment : Fragment(), MenuOnClickListener {
    private val appList: MutableList<String> = ArrayList()
    private val appIds:  MutableList<String> = ArrayList()
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
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    private fun getMenuList(): MutableList<String> {
        appList.add("পল্লীবিদ্যুৎ সেবা")
        appIds.add("org.breb.pollibiddut")
        appList.add("পল্লী বিদ্যুৎ ডিজিটাল ফোনবুক")
        appIds.add("reb.ebook.contackbook")
        appList.add("নাটোর জেলা পরিষদ")
        appIds.add("com.mysoftheaven.natore")
        appList.add("বিপিডিবি")
        appIds.add("com.sarwar.bpdb")
        appList.add("নথি | অফিস ব্যবস্থাপনা")
        appIds.add("com.tappware.nothipro")
        return appList
    }

    override fun menuOnClick(menuText: String) {
        var appId : String? = null
        for(i in 0 until appList.size){
            if(appList[i] == menuText) {
                appId = appIds[i]
                break
            }
        }
        if(appId != null)
            openPlayStore(requireContext(), appId)
    }
}