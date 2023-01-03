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
import com.galib.natorepbs2.utils.Utility
import org.json.JSONArray
import org.json.JSONObject

class WebsitesFragment : Fragment(), MenuOnClickListener  {
    val list : MutableList<String> = ArrayList()
    val urlList : MutableList<String> = ArrayList()

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
        getMenuList()
        binding.adapter = MenuAdapter(requireContext(),this, list)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }
    private fun getMenuList(): MutableList<String> {
        list.clear()
        urlList.clear()
        val json = Utility.getJsonFromAssets("init_data.json", requireContext().assets)
        if(json != null){
            val jsonRootObject = JSONObject(json)
            val jsonArray: JSONArray? = jsonRootObject.optJSONArray("pbs")
            if(jsonArray != null) {
                for (i in 0 until jsonArray.length()) {
                    list.add(jsonArray.getJSONObject(i).getString("name"))
                    urlList.add(jsonArray.getJSONObject(i).getString("url"))
                }
            }
        }
        return list
    }

    override fun menuOnClick(menuText: String) {
        var url:String? = null
        for(i in 0 until list.size){
            if(list[i] == menuText) {
                url = urlList[i]
            }
        }
        if(url != null){
            val action = WebsitesFragmentDirections.actionWebsitesFragmentToWebViewFragment(menuText, url, null, null)
            findNavController().navigate(action)

        }
    }
}