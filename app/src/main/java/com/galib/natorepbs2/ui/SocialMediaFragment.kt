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
import com.galib.natorepbs2.databinding.FragmentSocialMediaBinding

class SocialMediaFragment : Fragment() {

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
        binding.facebook = getString(R.string.menu_facebook_page)
        binding.fragment = this
        binding.lifecycleOwner = activity
        return binding.root
    }

    fun onClick(v: View) {
        when (v.id) {
            R.id.facebook_btn -> {
                val uri = getFacebookPageURL(requireActivity().applicationContext)
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(uri)))
            }
        }
    }
}