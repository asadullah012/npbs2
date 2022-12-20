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
import com.galib.natorepbs2.databinding.FragmentNoticeTenderBinding;


public class NoticeTenderFragment extends Fragment {

    public NoticeTenderFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentNoticeTenderBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_notice_tender, container,false);
        binding.setPageTitle(getString(R.string.menu_notice_tender));
        binding.setNotice(getString(R.string.menu_notice));
        binding.setTender(getString(R.string.menu_tender));
        binding.setNews(getString(R.string.menu_news));
        binding.setJob(getString(R.string.menu_job));
        binding.setFragment(this);
        binding.setLifecycleOwner(getActivity());
        return binding.getRoot();
    }
    public void onClick(View v){
        //Navigation.findNavController(v).navigate(R.id.action_main_to_aboutUsFragment);
        int id = v.getId();
        if (id == R.id.tender_btn) {
            NoticeTenderFragmentDirections.ActionNoticeTenderFragmentToNoticeInformationFragment action = NoticeTenderFragmentDirections.actionNoticeTenderFragmentToNoticeInformationFragment(getString(R.string.menu_tender));
            Navigation.findNavController(v).navigate(action);
        } else if(id == R.id.notice_btn){
            NoticeTenderFragmentDirections.ActionNoticeTenderFragmentToNoticeInformationFragment action = NoticeTenderFragmentDirections.actionNoticeTenderFragmentToNoticeInformationFragment(getString(R.string.menu_notice));
            Navigation.findNavController(v).navigate(action);
        } else if(id == R.id.news_btn){
            NoticeTenderFragmentDirections.ActionNoticeTenderFragmentToNoticeInformationFragment action = NoticeTenderFragmentDirections.actionNoticeTenderFragmentToNoticeInformationFragment(getString(R.string.menu_news));
            Navigation.findNavController(v).navigate(action);
        } else if(id == R.id.job_btn){
            NoticeTenderFragmentDirections.ActionNoticeTenderFragmentToNoticeInformationFragment action = NoticeTenderFragmentDirections.actionNoticeTenderFragmentToNoticeInformationFragment(getString(R.string.menu_job));
            Navigation.findNavController(v).navigate(action);
        }
    }
}