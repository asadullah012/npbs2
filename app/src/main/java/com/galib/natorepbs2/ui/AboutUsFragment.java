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
import com.galib.natorepbs2.Utility;
import com.galib.natorepbs2.databinding.FragmentAboutUsBinding;

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
        binding.setPageTitle(getString(R.string.about));
        binding.setAchievement(getString(R.string.achievement));
        binding.setAtAGlance(getString(R.string.at_a_glance));
        binding.setVisionMission(getString(R.string.vision_mission));
        binding.setComplainCentre(getString(R.string.complain_centre));
        binding.setOfficerList(getString(R.string.officer_list));
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
        }
    }
}