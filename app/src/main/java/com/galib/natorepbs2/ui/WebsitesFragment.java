package com.galib.natorepbs2.ui;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.galib.natorepbs2.R;
import com.galib.natorepbs2.databinding.FragmentWebsitesBinding;


public class WebsitesFragment extends Fragment {

    public WebsitesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentWebsitesBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_websites, container,false);
        binding.setPageTitle(getString(R.string.menu_websites));
        binding.setWebsiteBreb(getString(R.string.menu_breb));
        binding.setWebsiteNatorePbs2(getString(R.string.menu_natore_pbs2));
        binding.setFragment(this);
        binding.setLifecycleOwner(getActivity());
        return binding.getRoot();
    }
    public void onClick(View v){
        //Navigation.findNavController(v).navigate(R.id.action_main_to_aboutUsFragment);
        int id = v.getId();
        if (id == R.id.breb_btn) {

        } else if (id == R.id.natore_pbs2_btn) {

        }
    }
}