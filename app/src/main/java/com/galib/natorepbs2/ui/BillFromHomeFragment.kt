package com.galib.natorepbs2.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.galib.natorepbs2.R
import com.galib.natorepbs2.constants.Category
import com.galib.natorepbs2.databinding.FragmentBillFromHomeBinding

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
        binding.rocketApp = getString(R.string.menu_bill_rocket_app)
        binding.rocketUssd = getString(R.string.menu_bill_rocket_USSD)
        binding.gpayApp = getString(R.string.menu_bill_gpay_app)
        binding.gpayUssd = getString(R.string.menu_bill_gpay_USSD)
        binding.upayApp = getString(R.string.menu_bill_upay_app)
        binding.ucashUssd = getString(R.string.menu_bill_ucash_USSD)
        binding.fragment = this
        binding.lifecycleOwner = activity
        return binding.root
    }

    fun onClick(v: View) {
        when (v.id) {
            R.id.bkash_app_btn -> {
                val action = BillFromHomeFragmentDirections.actionBillFromHomeFragmentToBillInstructionsFragment(getString(R.string.menu_bill_bkash), Category.BKASH_APP)
                findNavController().navigate(action)
            }
            R.id.bkash_ussd_btn -> {
                val action = BillFromHomeFragmentDirections.actionBillFromHomeFragmentToBillInstructionsFragment(getString(R.string.menu_bill_bKash_USSD), Category.BKASH_USSD)
                findNavController().navigate(action)
            }
            R.id.rocket_app_btn -> {
                val action = BillFromHomeFragmentDirections.actionBillFromHomeFragmentToBillInstructionsFragment(getString(R.string.menu_bill_rocket_app), Category.ROCKET_APP)
                findNavController().navigate(action)
            }
            R.id.rocket_ussd_btn -> {
                val action = BillFromHomeFragmentDirections.actionBillFromHomeFragmentToBillInstructionsFragment(getString(R.string.menu_bill_rocket_USSD), Category.ROCKET_USSD)
                findNavController().navigate(action)
            }
            R.id.gpay_app_btn -> {
                val action = BillFromHomeFragmentDirections.actionBillFromHomeFragmentToBillInstructionsFragment(getString(R.string.menu_bill_gpay_app), Category.GPAY_APP)
                findNavController().navigate(action)
            }
            R.id.gpay_ussd_btn -> {
                val action = BillFromHomeFragmentDirections.actionBillFromHomeFragmentToBillInstructionsFragment(getString(R.string.menu_bill_gpay_USSD), Category.GPAY_USSD)
                findNavController().navigate(action)
            }
            R.id.upay_app_btn -> {
                val action = BillFromHomeFragmentDirections.actionBillFromHomeFragmentToBillInstructionsFragment(getString(R.string.menu_bill_upay_app), Category.UPAY_APP)
                findNavController().navigate(action)
            }
            R.id.ucash_ussd_btn -> {
                val action = BillFromHomeFragmentDirections.actionBillFromHomeFragmentToBillInstructionsFragment(getString(R.string.menu_bill_ucash_USSD), Category.UCASH_USSD)
                findNavController().navigate(action)
            }
        }
    }
}