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
import com.galib.natorepbs2.utils.Utility;
import com.galib.natorepbs2.databinding.FragmentOfficersBinding;
import com.galib.natorepbs2.viewmodel.EmployeeViewModel;

public class OfficersFragment extends Fragment {

    private EmployeeViewModel employeeViewModel;

    public OfficersFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentOfficersBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_officers, container,false);
        binding.setPageTitle(getString(R.string.menu_officers));
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
        employeeViewModel.getOfficerList().observe(getViewLifecycleOwner(), adapter::submitList);
        binding.setAdapter(adapter);
        return binding.getRoot();
    }
}