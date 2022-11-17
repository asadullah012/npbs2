package com.galib.natorepbs2.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.galib.natorepbs2.R;
import com.galib.natorepbs2.Utility;
import com.galib.natorepbs2.databinding.FragmentMainBinding;

public class MainFragment extends Fragment {

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentMainBinding binding =  DataBindingUtil.inflate(inflater,R.layout.fragment_main, container,false);
        binding.setTitle(getString(R.string.title));
        binding.setAbout(getString(R.string.about));
        binding.setService(getString(R.string.services));
        binding.setBannerContentDescription(getString(R.string.banner_content_description));
        binding.setLifecycleOwner(getActivity());
        binding.setFragment(this);
        return binding.getRoot();
    }

    public void onClick(View v){
        if(v.getId() == R.id.aboutUsBtn){
            Navigation.findNavController(v).navigate(R.id.action_main_to_aboutUsFragment);
//            Navigation.createNavigateOnClickListener(R.id.action_main_to_aboutUsFragment, null);
        }
    }
}