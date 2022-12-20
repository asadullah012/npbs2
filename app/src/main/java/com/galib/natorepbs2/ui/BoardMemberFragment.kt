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
import androidx.lifecycle.ViewModelProvider
import com.galib.natorepbs2.NPBS2Application
import com.galib.natorepbs2.R
import com.galib.natorepbs2.databinding.FragmentBoardMemberBinding
import com.galib.natorepbs2.db.Employee
import com.galib.natorepbs2.ui.ContactListAdapter.ClickListener
import com.galib.natorepbs2.ui.ContactListAdapter.OfficerDiff
import com.galib.natorepbs2.utils.Utility.Companion.bnDigitToEnDigit
import com.galib.natorepbs2.viewmodel.EmployeeViewModel
import com.galib.natorepbs2.viewmodel.EmployeeViewModelFactory
import com.galib.natorepbs2.viewmodel.InformationViewModel
import com.galib.natorepbs2.viewmodel.InformationViewModelFactory

class BoardMemberFragment : Fragment() {
    private val employeeViewModel: EmployeeViewModel by viewModels {
        EmployeeViewModelFactory((activity?.application as NPBS2Application).repository)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentBoardMemberBinding>(
            inflater,
            R.layout.fragment_board_member,
            container,
            false
        )
        binding.pageTitle = getString(R.string.menu_samity_board)
        binding.lifecycleOwner = activity

        val adapter = ContactListAdapter(object : ClickListener {
            override fun onClickCall(mobile: String?) {
                val intent = Intent(
                    Intent.ACTION_DIAL, Uri.parse(
                        "tel:" + bnDigitToEnDigit(
                            mobile!!
                        )
                    )
                )
                startActivity(intent)
            }

            override fun onClickEmail(email: String?) {
                val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:$email"))
                startActivity(intent)
            }
        })
        employeeViewModel.boardMemberList.observe(viewLifecycleOwner) { list ->
            adapter.submitList(
                list
            )
        }
        binding.adapter = adapter
        return binding.root
    }
}