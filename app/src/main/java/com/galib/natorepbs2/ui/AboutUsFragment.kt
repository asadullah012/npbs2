package com.galib.natorepbs2.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import com.galib.natorepbs2.R
import com.galib.natorepbs2.databinding.FragmentAboutUsBinding
import com.galib.natorepbs2.utils.Utility.Companion.openActivity

class AboutUsFragment : Fragment() {
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
        binding.achievement = getString(R.string.menu_our_achievements)
        binding.atAGlance = getString(R.string.menu_at_a_glance)
        binding.visionMission = getString(R.string.menu_vision_mission)
        binding.complainCentre = getString(R.string.menu_complain_centres)
        binding.officerList = getString(R.string.menu_officers)
        binding.juniorOfficerList = getString(R.string.menu_junior_officers)
        binding.samityBoard = getString(R.string.menu_samity_board)
        binding.officerList = getString(R.string.menu_officers)
        binding.officeHead = getString(R.string.menu_office_head)
        binding.officeList = getString(R.string.menu_offices)
        binding.powerOutageContact = getString(R.string.menu_power_outage_contact)
        binding.fragment = this
        binding.lifecycleOwner = activity
        return binding.root
    }

    fun onClick(v: View) {
        //Navigation.findNavController(v).navigate(R.id.action_main_to_aboutUsFragment);
        when (v.id) {
            R.id.atAGlanceBtn -> {
                findNavController(v).navigate(R.id.action_aboutUsFragment_to_atAGlanceFragment)
            }
            R.id.visionMissionBtn -> {
                openActivity(requireContext(), VisionMissionActivity::class.java)
            }
            R.id.achievementBtn -> {
                openActivity(requireContext(), AchievementActivity::class.java)
            }
            R.id.complainCentreBtn -> {
                openActivity(requireContext(), ComplainCentreActivity::class.java)
            }
            R.id.officersListBtn -> {
                findNavController(v).navigate(R.id.action_aboutUsFragment_to_officersFragment)
            }
            R.id.juniorOfficerBtn -> {
                findNavController(v).navigate(R.id.action_aboutUsFragment_to_juniorOfficerFragment)
            }
            R.id.boardMemberBtn -> {
                findNavController(v).navigate(R.id.action_aboutUsFragment_to_boardMemberFragment)
            }
            R.id.powerOutageContactBtn -> {
                findNavController(v).navigate(R.id.action_aboutUsFragment_to_powerOutageContactFragment)
            }
            R.id.officeHeadBtn -> {
                findNavController(v).navigate(R.id.action_aboutUsFragment_to_officeHeadFragment)
            }
            R.id.officeListBtn -> {
                findNavController(v).navigate(R.id.action_aboutUsFragment_to_officesFragment)
            }
        }
    }
}