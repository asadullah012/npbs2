package com.galib.natorepbs2.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.galib.natorepbs2.R
import com.galib.natorepbs2.constants.Category
import com.galib.natorepbs2.constants.URLs
import com.galib.natorepbs2.databinding.FragmentBillFromHomeBinding
import com.galib.natorepbs2.models.Instruction

class BillFromHomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DataBindingUtil.inflate<FragmentBillFromHomeBinding>(
            inflater,
            R.layout.fragment_bill_from_home,
            container,
            false
        )
        binding.pageTitle = getString(R.string.menu_electricity_bill)
        binding.bkashApp = getString(R.string.menu_bill_bkash)
        binding.bkashUssd = getString(R.string.menu_bill_bKash_USSD)
        binding.fragment = this
        binding.lifecycleOwner = activity
        return binding.root
    }

    fun onClick(v: View) {
        //Navigation.findNavController(v).navigate(R.id.action_main_to_aboutUsFragment);
        val id = v.id
        if (id == R.id.bkash_app_btn) {
            val action = BillFromHomeFragmentDirections.actionBillFromHomeFragmentToBillInstructionsFragment(getString(R.string.menu_bill_bkash), Category.BKASH_APP)
            Navigation.findNavController(v).navigate(action)
        } else if (id == R.id.bkash_ussd_btn) {
            val action = BillFromHomeFragmentDirections.actionBillFromHomeFragmentToBillInstructionsFragment(getString(R.string.menu_bill_bKash_USSD), Category.BKASH_USSD)
            Navigation.findNavController(v).navigate(action)
        }
    }
}