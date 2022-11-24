package com.galib.natorepbs2.ui;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.galib.natorepbs2.R;
import com.galib.natorepbs2.constants.URLs;
import com.galib.natorepbs2.databinding.FragmentOpinionComplainBinding;

public class OpinionComplainFragment extends Fragment {

    public OpinionComplainFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentOpinionComplainBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_opinion_complain, container,false);
        binding.setPageTitle(getString(R.string.menu_opinion_complain));
        binding.setGrs(getString(R.string.menu_grs));
        binding.setComplainGoogleForm(getString(R.string.menu_complain_google_form));
        binding.setFragment(this);
        binding.setLifecycleOwner(getActivity());
        return binding.getRoot();
    }
    public void onClick(View v){
        //Navigation.findNavController(v).navigate(R.id.action_main_to_aboutUsFragment);
        int id = v.getId();
        if (id == R.id.grs_btn) {
            OpinionComplainFragmentDirections.ActionOpinionComplainFragmentToWebViewFragment action = OpinionComplainFragmentDirections.actionOpinionComplainFragmentToWebViewFragment(getString(R.string.menu_grs), URLs.GRS, null, null);
            Navigation.findNavController(v).navigate(action);
        } else if (id == R.id.complain_google_form_btn) {
            OpinionComplainFragmentDirections.ActionOpinionComplainFragmentToWebViewFragment action = OpinionComplainFragmentDirections.actionOpinionComplainFragmentToWebViewFragment(getString(R.string.menu_complain_google_form), URLs.COMPLAIN_GOOGLE_FORM, null, null);
            Navigation.findNavController(v).navigate(action);
        }
    }
}