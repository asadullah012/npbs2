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
import com.galib.natorepbs2.databinding.FragmentJuniorOfficerBinding;
import com.galib.natorepbs2.databinding.FragmentOfficersBinding;
import com.galib.natorepbs2.viewmodel.EmployeeViewModel;

public class JuniorOfficerFragment extends Fragment {
    private EmployeeViewModel employeeViewModel;

    public JuniorOfficerFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentJuniorOfficerBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_junior_officer, container,false);
        binding.setPageTitle(getString(R.string.junior_officer_list));
        binding.setLifecycleOwner(getActivity());
        employeeViewModel = new ViewModelProvider(getActivity()).get(EmployeeViewModel.class);
        final OfficerListAdapter adapter = new OfficerListAdapter(new OfficerListAdapter.OfficerDiff(), new OfficerListAdapter.ClickListener() {
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
        employeeViewModel.getJuniorOfficerList().observe(getViewLifecycleOwner(), adapter::submitList);
        binding.setAdapter(adapter);
        return binding.getRoot();
    }
}