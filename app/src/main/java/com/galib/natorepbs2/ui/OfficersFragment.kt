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
import com.galib.natorepbs2.databinding.FragmentOfficersBinding
import com.galib.natorepbs2.models.Employee
import com.galib.natorepbs2.ui.ContactListAdapter.ClickListener
import com.galib.natorepbs2.utils.Utility.Companion.bnDigitToEnDigit
import com.galib.natorepbs2.viewmodel.EmployeeViewModel
import com.galib.natorepbs2.viewmodel.EmployeeViewModelFactory

class OfficersFragment : Fragment() {
    private val employeeViewModel: EmployeeViewModel by viewModels {
        EmployeeViewModelFactory((activity?.application as NPBS2Application).repository)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DataBindingUtil.inflate<FragmentOfficersBinding>(
            inflater,
            R.layout.fragment_officers,
            container,
            false
        )
        binding.pageTitle = getString(R.string.menu_officers)
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
        employeeViewModel.officerList.observe(viewLifecycleOwner) { list: List<Employee?> ->
            adapter.submitList(
                list
            )
        }
        binding.adapter = adapter
        return binding.root
    }
}