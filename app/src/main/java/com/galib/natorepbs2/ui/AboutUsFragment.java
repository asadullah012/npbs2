package com.galib.natorepbs2.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.galib.natorepbs2.R;
import com.galib.natorepbs2.databinding.FragmentAboutUsBinding;
import com.galib.natorepbs2.utils.Utility;

public class AboutUsFragment extends Fragment {
    public AboutUsFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentAboutUsBinding binding =  DataBindingUtil.inflate(inflater, R.layout.fragment_about_us, container,false);
        binding.setPageTitle(getString(R.string.menu_about_us));
        binding.setAchievement(getString(R.string.menu_our_achievements));
        binding.setAtAGlance(getString(R.string.menu_at_a_glance));
        binding.setVisionMission(getString(R.string.menu_vision_mission));
        binding.setComplainCentre(getString(R.string.menu_complain_centres));
        binding.setOfficerList(getString(R.string.menu_officers));
        binding.setJuniorOfficerList(getString(R.string.menu_junior_officers));
        binding.setSamityBoard(getString(R.string.menu_samity_board));
        binding.setOfficerList(getString(R.string.menu_officers));
        binding.setOfficeHead(getString(R.string.menu_office_head));
        binding.setOfficeList(getString(R.string.menu_offices));
        binding.setPowerOutageContact(getString(R.string.menu_power_outage_contact));
        binding.setFragment(this);
        binding.setLifecycleOwner(getActivity());
        return binding.getRoot();
    }
    public void onClick(View v){
        //Navigation.findNavController(v).navigate(R.id.action_main_to_aboutUsFragment);
        int id = v.getId();
        if (id == R.id.atAGlanceBtn) {
            Navigation.findNavController(v).navigate(R.id.action_aboutUsFragment_to_atAGlanceFragment);
        } else if (id == R.id.visionMissionBtn) {
            Utility.openActivity(getActivity(), VisionMissionActivity.class);
        } else if (id == R.id.achievementBtn) {
            Utility.openActivity(getActivity(), AchievementActivity.class);
        } else if (id == R.id.complainCentreBtn) {
            Utility.openActivity(getActivity(), ComplainCentreActivity.class);
        } else if(id == R.id.officersListBtn){
            Navigation.findNavController(v).navigate(R.id.action_aboutUsFragment_to_officersFragment);
        } else if(id == R.id.juniorOfficerBtn){
            Navigation.findNavController(v).navigate(R.id.action_aboutUsFragment_to_juniorOfficerFragment);
        } else if(id == R.id.boardMemberBtn){
            Navigation.findNavController(v).navigate(R.id.action_aboutUsFragment_to_boardMemberFragment);
        } else if(id == R.id.powerOutageContactBtn){
            Navigation.findNavController(v).navigate(R.id.action_aboutUsFragment_to_powerOutageContactFragment);
        } else if(id == R.id.officeHeadBtn){
            Navigation.findNavController(v).navigate(R.id.action_aboutUsFragment_to_officeHeadFragment);
        } else if(id == R.id.officeListBtn){
            Navigation.findNavController(v).navigate(R.id.action_aboutUsFragment_to_officesFragment);
        }
    }
}