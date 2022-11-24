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
import com.galib.natorepbs2.databinding.FragmentElectricityBillBinding;

public class ElectricityBillFragment extends Fragment {

    public ElectricityBillFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentElectricityBillBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_electricity_bill, container,false);
        binding.setPageTitle(getString(R.string.menu_electricity_bill));
        binding.setElectricityTariff(getString(R.string.menu_electricity_tariff));
        binding.setSmsBill(getString(R.string.menu_sms_electricity_bill));
        binding.setBillCollectionBank(getString(R.string.menu_bill_collection_bank));
        binding.setBillFromHome(getString(R.string.menu_bill_from_home));
        binding.setBillCalculator(getString(R.string.menu_electricity_bill_calculator));
        binding.setFragment(this);
        binding.setLifecycleOwner(getActivity());
        return binding.getRoot();
    }
    public void onClick(View v){
        //Navigation.findNavController(v).navigate(R.id.action_main_to_aboutUsFragment);
        int id = v.getId();
        if (id == R.id.bill_from_home_btn) {
            Navigation.findNavController(v).navigate(R.id.action_electricityBillFragment_to_billFromHomeFragment);
        } else if(id == R.id.electricity_tarriff_btn){
            ElectricityBillFragmentDirections.ActionElectricityBillFragmentToWebViewFragment action = ElectricityBillFragmentDirections.actionElectricityBillFragmentToWebViewFragment(getString(R.string.menu_electricity_tariff), URLs.BASE + URLs.TARIFF, null, Selectors.TARIFF);
            Navigation.findNavController(v).navigate(action);
        }
    }
}