package com.galib.natorepbs2.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.galib.natorepbs2.R
import com.galib.natorepbs2.databinding.FragmentOtherOfficeContactsBinding
import com.galib.natorepbs2.utils.Utility
import org.json.JSONArray
import org.json.JSONObject

class OtherOfficeContactsFragment : Fragment(), MenuOnClickListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentOtherOfficeContactsBinding>(
            inflater,
            R.layout.fragment_other_office_contacts,
            container,
            false
        )
        binding.pageTitle = getString(R.string.menu_other_official_contacts)
        binding.adapter = MenuAdapter(requireContext(),this, getMenuList())
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    private fun getMenuList(): MutableList<String> {
        val list : MutableList<String> = ArrayList()
        list.add("বাংলাদেশ পল্লী বিদ্যুতায়ন বোর্ড")
        val json: String? = Utility.getJsonFromAssets("init_data.json", requireContext().assets)
        if(json != null){
            val jsonRootObject = JSONObject(json)
            val jsonArray: JSONArray? = jsonRootObject.optJSONArray("otherOffices")
            if(jsonArray != null) {
                for (i in 0 until jsonArray.length()) {
                    val jsonObject = jsonArray.getJSONObject(i)
                    list.add(jsonObject.getString("name"))
                }
            }
        }
        return list
    }

    override fun menuOnClick(menuText: String) {
        val action : OtherOfficeContactsFragmentDirections.ActionOtherOfficeContactsFragmentToContactListFragment = OtherOfficeContactsFragmentDirections.actionOtherOfficeContactsFragmentToContactListFragment(menuText)
        findNavController().navigate(action)
    }
}