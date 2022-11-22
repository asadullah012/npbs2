package com.galib.natorepbs2.ui;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.galib.natorepbs2.R;
import com.galib.natorepbs2.databinding.FragmentBillFromHomeBinding;

public class BillFromHomeFragment extends Fragment {

    public BillFromHomeFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentBillFromHomeBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_bill_from_home, container,false);
        binding.setPageTitle(getString(R.string.menu_electricity_bill));
        binding.setBkashApp(getString(R.string.menu_bill_bkash));
        binding.setBkashUssd(getString(R.string.menu_bill_bKash_USSD));
        binding.setFragment(this);
        binding.setLifecycleOwner(getActivity());
        return binding.getRoot();
    }
    public void onClick(View v){
        //Navigation.findNavController(v).navigate(R.id.action_main_to_aboutUsFragment);
        int id = v.getId();
        if (id == R.id.bkash_app_btn) {

        } else if (id == R.id.bkash_ussd_btn) {

        }
    }
}