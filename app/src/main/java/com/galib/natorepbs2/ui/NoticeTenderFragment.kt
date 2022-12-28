package com.galib.natorepbs2.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.galib.natorepbs2.R
import com.galib.natorepbs2.databinding.FragmentNoticeTenderBinding

class NoticeTenderFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DataBindingUtil.inflate<FragmentNoticeTenderBinding>(
            inflater,
            R.layout.fragment_notice_tender,
            container,
            false
        )
        binding.pageTitle = getString(R.string.menu_notice_tender)
        binding.notice = getString(R.string.menu_notice)
        binding.tender = getString(R.string.menu_tender)
        binding.news = getString(R.string.menu_news)
        binding.job = getString(R.string.menu_job)
        binding.fragment = this
        binding.lifecycleOwner = activity
        return binding.root
    }

    fun onClick(v: View) {
        when (v.id) {
            R.id.tender_btn -> {
                val action =
                    NoticeTenderFragmentDirections.actionNoticeTenderFragmentToNoticeInformationFragment(
                        getString(R.string.menu_tender)
                    )
                findNavController().navigate(action)
            }
            R.id.notice_btn -> {
                val action =
                    NoticeTenderFragmentDirections.actionNoticeTenderFragmentToNoticeInformationFragment(
                        getString(R.string.menu_notice)
                    )
                findNavController().navigate(action)
            }
            R.id.news_btn -> {
                val action =
                    NoticeTenderFragmentDirections.actionNoticeTenderFragmentToNoticeInformationFragment(
                        getString(R.string.menu_news)
                    )
                findNavController().navigate(action)
            }
            R.id.job_btn -> {
                val action =
                    NoticeTenderFragmentDirections.actionNoticeTenderFragmentToNoticeInformationFragment(
                        getString(R.string.menu_job)
                    )
                findNavController().navigate(action)
            }
        }
    }
}