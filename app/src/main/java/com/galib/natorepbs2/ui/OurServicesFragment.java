package com.galib.natorepbs2.ui;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.galib.natorepbs2.R;
import com.galib.natorepbs2.databinding.FragmentOurServicesBinding;

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
        FragmentOurServicesBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_our_services, container,false);
        binding.setPageTitle(getString(R.string.menu_our_services));
        binding.setElectricityConnection(getString(R.string.menu_electricity_connection));
        binding.setElectricityBill(getString(R.string.menu_electricity_bill));
        binding.setCitizenCharter(getString(R.string.menu_citizen_charter));
        binding.setFragment(this);
        binding.setLifecycleOwner(getActivity());
        return binding.getRoot();
    }
    public void onClick(View v){
        //Navigation.findNavController(v).navigate(R.id.action_main_to_aboutUsFragment);
        int id = v.getId();
        if (id == R.id.electricity_connection_btn) {
            Navigation.findNavController(v).navigate(R.id.action_ourServicesFragment_to_electricityConnectionFragment);
        } else if (id == R.id.electricity_bill_btn) {
            Navigation.findNavController(v).navigate(R.id.action_ourServicesFragment_to_electricityBillFragment);
        } else if (id == R.id.citizen_charter_btn) {
            Navigation.findNavController(v).navigate(R.id.action_ourServicesFragment_to_PDFViewerFragment);
        }
    }
}