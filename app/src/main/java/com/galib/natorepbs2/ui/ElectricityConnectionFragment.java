package com.galib.natorepbs2.ui;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.galib.natorepbs2.R;
import com.galib.natorepbs2.databinding.FragmentElectricityConnectionBinding;

public class ElectricityConnectionFragment extends Fragment {

    public ElectricityConnectionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentElectricityConnectionBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_electricity_connection, container,false);
        binding.setPageTitle(getString(R.string.menu_electricity_connection));
        binding.setConnectionRules(getString(R.string.menu_electricity_connection_rules));
        binding.setConnectionDomestic(getString(R.string.menu_connection_domestic));
        binding.setConnectionIndustry(getString(R.string.menu_connection_industry));
        binding.setConnectionSms(getString(R.string.menu_sms_connection));
        binding.setFragment(this);
        binding.setLifecycleOwner(getActivity());
        return binding.getRoot();
    }
    public void onClick(View v){
        //Navigation.findNavController(v).navigate(R.id.action_main_to_aboutUsFragment);
        int id = v.getId();
        if (id == R.id.electricity_connection_btn) {

        }
    }
}