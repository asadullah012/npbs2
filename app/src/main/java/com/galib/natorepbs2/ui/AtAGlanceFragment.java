package com.galib.natorepbs2.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.galib.natorepbs2.R;
import com.galib.natorepbs2.constants.Category;
import com.galib.natorepbs2.databinding.FragmentAtAGlanceBinding;
import com.galib.natorepbs2.viewmodel.InformationViewModel;

public class AtAGlanceFragment extends Fragment {
    InformationViewModel informationViewModel;
    public AtAGlanceFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentAtAGlanceBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_at_a_glance, container,false);
        binding.setPageTitle(getString(R.string.menu_at_a_glance));
        binding.setLifecycleOwner(getActivity());
        informationViewModel = new ViewModelProvider(getActivity()).get(InformationViewModel.class);
        final InformationAdapter adapter = new InformationAdapter(new InformationAdapter.WordDiff());
        informationViewModel.getInformationByCategory(Category.atAGlance).observe(getViewLifecycleOwner(), adapter::submitList);
        informationViewModel.getMonth().observe(getViewLifecycleOwner(), information -> binding.setMonth(information.getTitle()));
        binding.setAdapter(adapter);
        return binding.getRoot();
    }

}