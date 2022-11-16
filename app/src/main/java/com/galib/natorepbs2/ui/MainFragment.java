package com.galib.natorepbs2.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

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
        binding.setTitle(getResources().getString(R.string.title));
        binding.setAbout(getResources().getString(R.string.about));
        binding.setService(getResources().getString(R.string.services));
        binding.setBannerContentDescription(getResources().getString(R.string.banner_content_description));
        binding.setLifecycleOwner(getActivity());
        binding.setFragment(this);
        return binding.getRoot();
    }

    public void onClick(View v){
        if(v.getId() == R.id.aboutUsBtn)
            Utility.openActivity(getActivity(), AboutUsActivity.class);
    }

}