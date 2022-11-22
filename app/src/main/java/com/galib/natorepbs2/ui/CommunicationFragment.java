package com.galib.natorepbs2.ui;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.galib.natorepbs2.R;
import com.galib.natorepbs2.databinding.FragmentCommunicationBinding;

public class CommunicationFragment extends Fragment {

    public CommunicationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentCommunicationBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_communication, container,false);
        binding.setPageTitle(getString(R.string.menu_communication));
        binding.setCommunicationPost(getString(R.string.menu_communication_post));
        binding.setCommunicationMap(getString(R.string.menu_communication_map));
        binding.setFragment(this);
        binding.setLifecycleOwner(getActivity());
        return binding.getRoot();
    }
    public void onClick(View v){
        //Navigation.findNavController(v).navigate(R.id.action_main_to_aboutUsFragment);
        int id = v.getId();
        if (id == R.id.communication_post_btn) {

        } else if (id == R.id.communication_map_btn) {

        }
    }
}