package com.galib.natorepbs2.ui;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.galib.natorepbs2.R;
import com.galib.natorepbs2.constants.URLs;
import com.galib.natorepbs2.databinding.FragmentRelatedAppsBinding;
import com.galib.natorepbs2.utils.Utility;

public class RelatedAppsFragment extends Fragment {

    public RelatedAppsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentRelatedAppsBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_related_apps, container,false);
        binding.setPageTitle(getString(R.string.menu_related_apps));
        binding.setDigitalPhonebook(getString(R.string.menu_digital_phonebook));
        binding.setNothi(getString(R.string.menu_nothi));
        binding.setFragment(this);
        binding.setLifecycleOwner(getActivity());
        return binding.getRoot();
    }
    public void onClick(View v){
        //Navigation.findNavController(v).navigate(R.id.action_main_to_aboutUsFragment);
        int id = v.getId();
        if (id == R.id.digital_phonebook_btn) {
            Utility.openPlayStore(getContext(), URLs.DIGITAL_PHONEBOOK_APP_ID);
        } else if (id == R.id.nothi_btn) {
            Utility.openPlayStore(getContext(), URLs.NOTHI_APP_ID);
        }
    }
}