package com.galib.natorepbs2.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.galib.natorepbs2.R;
import com.galib.natorepbs2.Utility;
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
        officeHead.observe(getViewLifecycleOwner(), employee -> {
            binding.setEmployee(employee);
        });
        binding.setFragment(this);
        binding.setLifecycleOwner(getActivity());
        Picasso.get().load("http://file-rajshahi.portal.gov.bd/files/pbs2.natore.gov.bd/officer_list/de0d2dc5_1f34_4774_b335_5bc5bc176719/de073abfb53ad0b918a8b67489a1d4bc.png").into((ImageView) binding.getRoot().findViewById(R.id.profilePhoto));
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