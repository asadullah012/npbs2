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
import com.galib.natorepbs2.databinding.FragmentMainBinding;
import com.galib.natorepbs2.databinding.FragmentMainV2Binding;

public class MainFragment extends Fragment {

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentMainV2Binding binding =  DataBindingUtil.inflate(inflater,R.layout.fragment_main_v2, container,false);
        binding.setTitle(getString(R.string.title));
        binding.setAbout(getString(R.string.menu_about_us));
        binding.setService(getString(R.string.menu_our_services));
        binding.setAwareness(getString(R.string.menu_awareness));
        binding.setCommunication(getString(R.string.menu_communication));
        binding.setOpinionComplain(getString(R.string.menu_opinion_complain));
        binding.setRelatedApps(getString(R.string.menu_related_apps));
        binding.setSocialMedia(getString(R.string.menu_social_media));
        binding.setWebsite(getString(R.string.menu_websites));
        binding.setNoticeTender(getString(R.string.menu_notice_tender));
        binding.setBannerContentDescription(getString(R.string.banner_content_description));
        binding.setLifecycleOwner(getActivity());
        binding.setFragment(this);
        return binding.getRoot();
    }

    public void onClick(View v){
        if(v.getId() == R.id.aboutUsBtn){
            Navigation.findNavController(v).navigate(R.id.action_main_to_aboutUsFragment);
//            Navigation.createNavigateOnClickListener(R.id.action_main_to_aboutUsFragment, null);
        } else if(v.getId() == R.id.serviceBtn){
            Navigation.findNavController(v).navigate(R.id.action_main_to_ourServicesFragment);
        } else if(v.getId() == R.id.websiteBtn){
            Navigation.findNavController(v).navigate(R.id.action_main_to_websitesFragment);
        } else if(v.getId() == R.id.social_media_btn){
            Navigation.findNavController(v).navigate(R.id.action_main_to_socialMediaFragment);
        } else if(v.getId() == R.id.opinion_complain_btn){
            Navigation.findNavController(v).navigate(R.id.action_main_to_opinionComplainFragment);
        } else if(v.getId() == R.id.related_apps_btn){
            Navigation.findNavController(v).navigate(R.id.action_main_to_relatedAppsFragment);
        } else if(v.getId() == R.id.notice_tender_btn){
            Navigation.findNavController(v).navigate(R.id.action_main_to_noticeTenderFragment);
        } else if(v.getId() == R.id.communication_btn){
            Navigation.findNavController(v).navigate(R.id.action_main_to_communicationFragment);
        } else if(v.getId() == R.id.awareness_btn){
            Navigation.findNavController(v).navigate(R.id.action_main_to_awarenessFragment);
        }
    }
}