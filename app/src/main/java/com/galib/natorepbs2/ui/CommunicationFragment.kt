package com.galib.natorepbs2.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import com.galib.natorepbs2.R
import com.galib.natorepbs2.constants.Selectors
import com.galib.natorepbs2.constants.URLs
import com.galib.natorepbs2.databinding.FragmentCommunicationBinding
import com.galib.natorepbs2.utils.Utility.Companion.openMap

class CommunicationFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentCommunicationBinding>(
            inflater,
            R.layout.fragment_communication,
            container,
            false
        )
        binding.pageTitle = getString(R.string.menu_communication)
        binding.communicationPost = getString(R.string.menu_communication_post)
        binding.communicationMap = getString(R.string.menu_communication_map)
        binding.fragment = this
        binding.lifecycleOwner = activity
        return binding.root
    }

    fun onClick(v: View) {
        //Navigation.findNavController(v).navigate(R.id.action_main_to_aboutUsFragment);
        val id = v.id
        if (id == R.id.communication_post_btn) {
            val action =
                CommunicationFragmentDirections.actionCommunicationFragmentToWebViewFragment(
                    getString(R.string.menu_communication_post),
                    URLs.BASE + URLs.COMMUNICATION_POST,
                    null,
                    Selectors.COMMUNICATION_POST
                )
            findNavController(v).navigate(action)
        } else if (id == R.id.communication_map_btn) {
            openMap(
                requireContext(),
                "Natore+Palli+Bidyut+Samity-2,+Bonpara,+Natore",
                24.295823,
                89.0811235
            )
        }
    }
}