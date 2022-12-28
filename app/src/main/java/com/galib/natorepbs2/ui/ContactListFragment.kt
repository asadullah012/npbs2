package com.galib.natorepbs2.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.galib.natorepbs2.NPBS2Application
import com.galib.natorepbs2.R
import com.galib.natorepbs2.databinding.FragmentContactListBinding
import com.galib.natorepbs2.databinding.FragmentOfficersBinding
import com.galib.natorepbs2.models.Employee
import com.galib.natorepbs2.utils.Utility
import com.galib.natorepbs2.viewmodel.EmployeeViewModel
import com.galib.natorepbs2.viewmodel.EmployeeViewModelFactory


class ContactListFragment : Fragment() {
    private val employeeViewModel: EmployeeViewModel by viewModels {
        EmployeeViewModelFactory((activity?.application as NPBS2Application).repository)
    }
    private val args: ContactListFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentContactListBinding>(
            inflater,
            R.layout.fragment_contact_list,
            container,
            false
        )

        binding.pageTitle = args.title + "\n" + getString(R.string.menu_officers)
        binding.lifecycleOwner = activity
        val adapter = ContactListAdapter(object : ContactListAdapter.ClickListener {
            override fun onClickCall(mobile: String?) {
                val intent = Intent(
                    Intent.ACTION_DIAL, Uri.parse(
                        "tel:" + Utility.bnDigitToEnDigit(
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

        employeeViewModel.getOfficerListByOffice(args.title).observe(viewLifecycleOwner) { list -> adapter.submitList(list) }
        binding.adapter = adapter
        return binding.root
    }
}