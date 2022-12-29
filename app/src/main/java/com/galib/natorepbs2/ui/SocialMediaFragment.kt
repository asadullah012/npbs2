package com.galib.natorepbs2.ui

import com.galib.natorepbs2.utils.Utility.Companion.getFacebookPageURL
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.galib.natorepbs2.R
import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.galib.natorepbs2.databinding.FragmentSocialMediaBinding
import com.galib.natorepbs2.sync.Sync
import com.galib.natorepbs2.utils.Utility
import org.json.JSONArray
import org.json.JSONObject

class SocialMediaFragment : Fragment(), MenuOnClickListener {
    val list : MutableList<String> = ArrayList()
    val urlList : MutableList<String> = ArrayList()
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
        binding.adapter = MenuAdapter(requireContext(),this, list)
        binding.lifecycleOwner = activity
        return binding.root
    }

    private fun getMenuList(): MutableList<String> {
        val json = Utility.getJsonFromAssets("init_data.json", requireContext().assets)
        if(json != null){
            val jsonRootObject = JSONObject(json)
            val jsonArray: JSONArray? = jsonRootObject.optJSONArray("pbsFacebook")
            if(jsonArray != null) {
                for (i in 0 until jsonArray.length()) {
                    list.add(jsonArray.getJSONObject(i).getString("name") + " " + getString(R.string.menu_facebook_page))
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