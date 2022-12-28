package com.galib.natorepbs2.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.galib.natorepbs2.R
import com.galib.natorepbs2.databinding.FragmentAboutUsBinding

class AboutUsFragment : Fragment(), MenuOnClickListener  {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DataBindingUtil.inflate<FragmentAboutUsBinding>(
            inflater,
            R.layout.fragment_about_us,
            container,
            false
        )
        binding.pageTitle = getString(R.string.menu_about_us)
        binding.adapter = MenuAdapter(requireContext(),this, getMenuList())
        binding.lifecycleOwner = activity
        return binding.root
    }

    private fun getMenuList(): MutableList<String> {
        val list : MutableList<String> = ArrayList()
        list.add(getString(R.string.menu_at_a_glance))
        list.add(getString(R.string.menu_vision_mission))
        list.add(getString(R.string.menu_our_achievements))
        list.add(getString(R.string.menu_samity_board))
        list.add(getString(R.string.menu_office_head))
        list.add(getString(R.string.menu_officers))
        list.add(getString(R.string.menu_junior_officers))
        list.add(getString(R.string.menu_offices))
        list.add(getString(R.string.menu_complain_centres))
        list.add(getString(R.string.menu_power_outage_contact))
        return list
    }

    override fun menuOnClick(menuText: String) {
        when (menuText) {
            getString(R.string.menu_at_a_glance) -> findNavController().navigate(R.id.action_aboutUsFragment_to_atAGlanceFragment)
            getString(R.string.menu_vision_mission) -> findNavController().navigate(R.id.action_aboutUsFragment_to_visionMissionFragment)
            getString(R.string.menu_our_achievements) -> findNavController().navigate(R.id.action_aboutUsFragment_to_achievementFragment)
            getString(R.string.menu_samity_board) -> findNavController().navigate(R.id.action_aboutUsFragment_to_boardMemberFragment)
            getString(R.string.menu_office_head) -> findNavController().navigate(R.id.action_aboutUsFragment_to_officeHeadFragment)
            getString(R.string.menu_officers) -> findNavController().navigate(R.id.action_aboutUsFragment_to_officersFragment)
            getString(R.string.menu_junior_officers) -> findNavController().navigate(R.id.action_aboutUsFragment_to_juniorOfficerFragment)
            getString(R.string.menu_offices) -> findNavController().navigate(R.id.action_aboutUsFragment_to_officesFragment)
            getString(R.string.menu_complain_centres) -> findNavController().navigate(R.id.action_aboutUsFragment_to_complainCentreFragment)
            getString(R.string.menu_power_outage_contact) -> findNavController().navigate(R.id.action_aboutUsFragment_to_powerOutageContactFragment)
        }
    }
}