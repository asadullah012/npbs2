package com.galib.natorepbs2.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.galib.natorepbs2.NPBS2Application
import com.galib.natorepbs2.R
import com.galib.natorepbs2.databinding.FragmentNoticeInformationBinding
import com.galib.natorepbs2.viewmodel.NoticeInformationViewModel
import com.galib.natorepbs2.viewmodel.NoticeViewModelFactory

class NoticeInformationFragment : Fragment() {
    private val args: NoticeInformationFragmentArgs by navArgs()
    private val noticeInformationViewModel: NoticeInformationViewModel by viewModels {
        NoticeViewModelFactory((activity?.application as NPBS2Application).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DataBindingUtil.inflate<FragmentNoticeInformationBinding>(inflater, R.layout.fragment_notice_information, container, false)
        binding.pageTitle = args.title
        binding.lifecycleOwner = activity
        val adapter = NoticeInformationAdapter()
        when (args.title) {
            getString(R.string.menu_tender) -> {
                noticeInformationViewModel.getAllTender().observe(viewLifecycleOwner) { allTender ->
                    allTender?.let { adapter.differ.submitList(it) }
                }
            }
            getString(R.string.menu_notice) -> {
                noticeInformationViewModel.getAllNotice().observe(viewLifecycleOwner) { allNotice ->
                    allNotice?.let { adapter.differ.submitList(it) }
                }
            }
            getString(R.string.menu_news) -> {
                noticeInformationViewModel.getAllNews().observe(viewLifecycleOwner) { allNews ->
                    allNews?.let { adapter.differ.submitList(it) }
                }
            }
            getString(R.string.menu_job) -> {
                noticeInformationViewModel.getAllJob().observe(viewLifecycleOwner) { allJob ->
                    allJob?.let { adapter.differ.submitList(it) }
                }
            }
        }
        binding.adapter = adapter
        return binding.root
    }

}