package com.galib.natorepbs2.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.galib.natorepbs2.R;
import com.galib.natorepbs2.utils.Utility;
import com.galib.natorepbs2.databinding.FragmentOfficeHeadBinding;
import com.galib.natorepbs2.db.Employee;
import com.galib.natorepbs2.viewmodel.EmployeeViewModel;
import com.squareup.picasso.Picasso;

public class OfficeHeadFragment extends Fragment {
    LiveData<Employee> officeHead;
    EmployeeViewModel employeeViewModel;
    public OfficeHeadFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        employeeViewModel = new ViewModelProvider(this).get(EmployeeViewModel.class);
        officeHead = employeeViewModel.getOfficeHead();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentOfficeHeadBinding binding =  DataBindingUtil.inflate(inflater, R.layout.fragment_office_head, container,false);
        binding.setPageTitle(getString(R.string.menu_office_head));
        binding.setFragment(this);
        binding.setLifecycleOwner(getActivity());
        officeHead.observe(getViewLifecycleOwner(), employee -> {
            binding.setEmployee(employee);
            binding.executePendingBindings();
        });
        return binding.getRoot();
    }

    public void onClick(View v, String s){
        int id = v.getId();
        if(id == R.id.callBtn){
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + Utility.bnDigitToEnDigit(s)));
            startActivity(intent);
        } else if( id == R.id.emailBtn){
            Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + s));
            startActivity(intent);
        }
    }
}