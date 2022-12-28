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
import com.galib.natorepbs2.databinding.FragmentOpinionComplainBinding

class OpinionComplainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DataBindingUtil.inflate<FragmentOpinionComplainBinding>(
            inflater,
            R.layout.fragment_opinion_complain,
            container,
            false
        )
        binding.pageTitle = getString(R.string.menu_opinion_complain)
        binding.grs = getString(R.string.menu_grs)
        binding.complainGoogleForm = getString(R.string.menu_complain_google_form)
        binding.fragment = this
        binding.lifecycleOwner = activity
        return binding.root
    }

    fun onClick(v: View) {
        when(v.id){
            R.id.grs_btn -> {
                val action =
                    OpinionComplainFragmentDirections.actionOpinionComplainFragmentToWebViewFragment(getString(R.string.menu_grs), URLs.GRS, null, null)
                findNavController().navigate(action)
            }
            R.id.complain_google_form_btn -> {
                val action =
                    OpinionComplainFragmentDirections.actionOpinionComplainFragmentToWebViewFragment(getString(R.string.menu_complain_google_form), URLs.COMPLAIN_GOOGLE_FORM, null, null)
                findNavController().navigate(action)
            }
        }
    }
}