package com.galib.natorepbs2.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.galib.natorepbs2.R;
import com.galib.natorepbs2.constants.Selectors;
import com.galib.natorepbs2.constants.URLs;
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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
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
        if (id == R.id.electricity_connection_rules_btn) {
            ElectricityConnectionFragmentDirections.ActionElectricityConnectionFragmentToWebViewFragment action
                    = ElectricityConnectionFragmentDirections.actionElectricityConnectionFragmentToWebViewFragment(getString(R.string.menu_electricity_connection_rules), URLs.BASE + URLs.ELECTRICITY_CONNECTION_RULES, null, Selectors.ELECTRICITY_CONNECTION_RULES);
            Navigation.findNavController(v).navigate(action);
        } else if(id == R.id.connection_domestic_btn){
            ElectricityConnectionFragmentDirections.ActionElectricityConnectionFragmentToWebViewFragment action
                    = ElectricityConnectionFragmentDirections.actionElectricityConnectionFragmentToWebViewFragment(getString(R.string.menu_connection_domestic), URLs.CONNECTOION_DOMESTIC, null, null);
            Navigation.findNavController(v).navigate(action);
        } else if(id == R.id.connection_industry_btn){
            ElectricityConnectionFragmentDirections.ActionElectricityConnectionFragmentToWebViewFragment action
                    = ElectricityConnectionFragmentDirections.actionElectricityConnectionFragmentToWebViewFragment(getString(R.string.menu_connection_domestic), URLs.CONNECTION_INDUSTRY, null, null);
            Navigation.findNavController(v).navigate(action);
        } else if(id == R.id.connection_sms_btn){
            Navigation.findNavController(v).navigate(R.id.action_electricityConnectionFragment_to_connectionMessageFragment);
        }
    }
}