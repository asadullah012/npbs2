package com.galib.natorepbs2.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.galib.natorepbs2.NPBS2Application
import com.galib.natorepbs2.R
import com.galib.natorepbs2.databinding.FragmentOfficesBinding
import com.galib.natorepbs2.ui.OfficesAdapter.OfficeInfoOnClickListener
import com.galib.natorepbs2.utils.Utility
import com.galib.natorepbs2.utils.Utility.Companion.bnDigitToEnDigit
import com.galib.natorepbs2.viewmodel.OfficeInformationViewModel
import com.galib.natorepbs2.viewmodel.OfficeViewModelFactory

class OfficesFragment : Fragment() {
    private val officeInformationViewModel: OfficeInformationViewModel by viewModels {
        OfficeViewModelFactory((activity?.application as NPBS2Application).repository)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentOfficesBinding>(inflater, R.layout.fragment_offices, container, false)
        binding.pageTitle = getString(R.string.menu_offices)
        binding.lifecycleOwner = activity
        val adapter = OfficesAdapter(object: OfficeInfoOnClickListener{
            override fun onClickCall(mobile: String?) {
                val intent =
                    Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + bnDigitToEnDigit(mobile!!)))
                startActivity(intent)
            }

            override fun onClickEmail(email: String?) {
                val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:$email"))
                startActivity(intent)
            }

            override fun onClickMap(mapUrl: String?) {
                Utility.openMap(context!!, mapUrl!!)
            }

        })
        officeInformationViewModel.getAllOfficeInfo().observe(viewLifecycleOwner) { list ->
            adapter.differ.submitList(list)
        }
        binding.adapter = adapter
        return binding.root
    }
}