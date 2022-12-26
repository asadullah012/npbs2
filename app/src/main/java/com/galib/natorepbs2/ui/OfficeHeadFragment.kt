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
import androidx.lifecycle.LiveData
import com.galib.natorepbs2.NPBS2Application
import com.galib.natorepbs2.R
import com.galib.natorepbs2.databinding.FragmentOfficeHeadBinding
import com.galib.natorepbs2.models.Employee
import com.galib.natorepbs2.utils.Utility.Companion.bnDigitToEnDigit
import com.galib.natorepbs2.viewmodel.EmployeeViewModel
import com.galib.natorepbs2.viewmodel.EmployeeViewModelFactory

class OfficeHeadFragment : Fragment() {
    private var officeHead: LiveData<Employee>? = null
    private val employeeViewModel: EmployeeViewModel by viewModels {
        EmployeeViewModelFactory((activity?.application as NPBS2Application).repository)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        officeHead = employeeViewModel.officeHead
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DataBindingUtil.inflate<FragmentOfficeHeadBinding>(
            inflater,
            R.layout.fragment_office_head,
            container,
            false
        )
        binding.pageTitle = getString(R.string.menu_office_head)
        binding.fragment = this
        binding.lifecycleOwner = activity
        officeHead!!.observe(viewLifecycleOwner) { employee: Employee? ->
            binding.employee = employee
            binding.executePendingBindings()
        }
        return binding.root
    }

    fun onClick(v: View, s: String) {
        val id = v.id
        if (id == R.id.callBtn) {
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + bnDigitToEnDigit(s)))
            startActivity(intent)
        } else if (id == R.id.emailBtn) {
            val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:$s"))
            startActivity(intent)
        }
    }
}