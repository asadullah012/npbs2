package com.galib.natorepbs2.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.galib.natorepbs2.R
import com.galib.natorepbs2.databinding.FragmentSocialMediaBinding
import com.galib.natorepbs2.sync.SyncConfig
import com.galib.natorepbs2.utils.Utility.Companion.getFacebookPageURL
import org.json.JSONArray

class SocialMediaFragment : Fragment(), MenuOnClickListener {
    val list : MutableList<String> = ArrayList()
    private val urlList : MutableList<String> = ArrayList()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DataBindingUtil.inflate<FragmentSocialMediaBinding>(
            inflater,
            R.layout.fragment_social_media,
            container,
            false
        )
        binding.pageTitle = getString(R.string.menu_social_media)
        getMenuList()
        val adapter = MenuAdapter(requireContext(),this, list)
        adapter.setIcon(R.drawable.ic_facebook)
        binding.adapter = adapter
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    private fun getMenuList(): MutableList<String> {
        list.clear()
        urlList.clear()
        val jsonRootObject = SyncConfig.getSyncDataJson(requireContext())
        if(jsonRootObject != null){
            val jsonArray: JSONArray? = jsonRootObject.optJSONArray("pbsFacebook")
            if(jsonArray != null) {
                for (i in 0 until jsonArray.length()) {
                    list.add(jsonArray.getJSONObject(i).getString("name") )
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
        if(url != null) {
            val uri = getFacebookPageURL(requireActivity().applicationContext,url)
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(uri)))
        }
    }
}