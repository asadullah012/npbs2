package com.galib.natorepbs2.ui;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.galib.natorepbs2.R;
import com.galib.natorepbs2.constants.Selectors;
import com.galib.natorepbs2.constants.URLs;
import com.galib.natorepbs2.databinding.FragmentCommunicationBinding;
import com.galib.natorepbs2.utils.Utility;

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
            CommunicationFragmentDirections.ActionCommunicationFragmentToWebViewFragment action
                    = CommunicationFragmentDirections.actionCommunicationFragmentToWebViewFragment(getString(R.string.menu_communication_post), URLs.BASE + URLs.COMMUNICATION_POST, null, Selectors.COMMUNICATION_POST);
            Navigation.findNavController(v).navigate(action);
        } else if (id == R.id.communication_map_btn) {
            Utility.openMap(getContext(), "Natore+Palli+Bidyut+Samity-2,+Bonpara,+Natore", 24.295823,89.0811235);
        }
    }
}