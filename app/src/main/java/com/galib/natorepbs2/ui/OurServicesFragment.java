package com.galib.natorepbs2.ui;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.galib.natorepbs2.R;
import com.galib.natorepbs2.Utility;
import com.galib.natorepbs2.databinding.FragmentOurServicesBinding;
import com.galib.natorepbs2.sync.SyncTariff;

public class OurServicesFragment extends Fragment {

    public OurServicesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentOurServicesBinding binding =  DataBindingUtil.inflate(inflater, R.layout.fragment_our_services, container,false);
        binding.setPageTitle(getString(R.string.services));
        binding.setElectricityPrice(getString(R.string.electricity_price));
        binding.setFragment(this);
        binding.setLifecycleOwner(getActivity());
        return binding.getRoot();
    }
    public void onClick(View v){
        //Navigation.findNavController(v).navigate(R.id.action_main_to_aboutUsFragment);
        int id = v.getId();
        if (id == R.id.electricityPrice) {
            new SyncTariff(getContext()).execute();
        }

    }
}