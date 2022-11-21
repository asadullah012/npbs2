package com.galib.natorepbs2.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.galib.natorepbs2.R;
import com.galib.natorepbs2.Utility;
import com.galib.natorepbs2.databinding.FragmentPowerOutageContactBinding;
import com.galib.natorepbs2.viewmodel.EmployeeViewModel;

public class PowerOutageContactFragment extends Fragment {

    private EmployeeViewModel employeeViewModel;

    public PowerOutageContactFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentPowerOutageContactBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_power_outage_contact, container,false);
        binding.setPageTitle(getString(R.string.power_outage_contact));
        binding.setLifecycleOwner(getActivity());
        employeeViewModel = new ViewModelProvider(getActivity()).get(EmployeeViewModel.class);
        final ContactListAdapter adapter = new ContactListAdapter(new ContactListAdapter.OfficerDiff(), new ContactListAdapter.ClickListener() {
            @Override
            public void onClickCall(String mobile) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + Utility.bnDigitToEnDigit(mobile)));
                startActivity(intent);
            }

            @Override
            public void onClickEmail(String email) {
                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + email));
                startActivity(intent);
            }
        });
        employeeViewModel.getPowerOutageContactList().observe(getViewLifecycleOwner(), adapter::submitList);
        binding.setAdapter(adapter);
        employeeViewModel.getHeaderText().observe(getViewLifecycleOwner(), information -> binding.setHeader(information));
        employeeViewModel.getFooterText().observe(getViewLifecycleOwner(), information -> binding.setFooter(information));
        return binding.getRoot();
    }
}