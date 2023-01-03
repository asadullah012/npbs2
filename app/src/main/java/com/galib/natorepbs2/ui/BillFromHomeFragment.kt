package com.galib.natorepbs2.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.galib.natorepbs2.R
import com.galib.natorepbs2.constants.Category
import com.galib.natorepbs2.databinding.FragmentBillFromHomeBinding

class BillFromHomeFragment : Fragment(), MenuOnClickListener {

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
        binding.pageTitle = getString(R.string.menu_bill_from_home)
        binding.adapter = MenuAdapter(requireContext(),this, getMenuList())
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    private fun getMenuList() : MutableList<String>{
        val list:MutableList<String> = ArrayList()
        list.add(getString(R.string.menu_bill_bkash))
        list.add(getString(R.string.menu_bill_bKash_USSD))
        list.add(getString(R.string.menu_bill_rocket_app))
        list.add(getString(R.string.menu_bill_rocket_USSD))
        list.add(getString(R.string.menu_bill_gpay_app))
        list.add(getString(R.string.menu_bill_gpay_USSD))
        list.add(getString(R.string.menu_bill_upay_app))
        list.add(getString(R.string.menu_bill_ucash_USSD))
        return list
    }

    override fun menuOnClick(menuText: String){
        var action:NavDirections? = null
        when (menuText) {
            getString(R.string.menu_bill_bkash) -> action = BillFromHomeFragmentDirections.actionBillFromHomeFragmentToBillInstructionsFragment(getString(R.string.menu_bill_bkash), Category.BKASH_APP)
            getString(R.string.menu_bill_bKash_USSD) -> action = BillFromHomeFragmentDirections.actionBillFromHomeFragmentToBillInstructionsFragment(getString(R.string.menu_bill_bKash_USSD), Category.BKASH_USSD)
            getString(R.string.menu_bill_rocket_app) -> action = BillFromHomeFragmentDirections.actionBillFromHomeFragmentToBillInstructionsFragment(getString(R.string.menu_bill_rocket_app), Category.ROCKET_APP)
            getString(R.string.menu_bill_rocket_USSD) ->  action = BillFromHomeFragmentDirections.actionBillFromHomeFragmentToBillInstructionsFragment(getString(R.string.menu_bill_rocket_USSD), Category.ROCKET_USSD)
            getString(R.string.menu_bill_gpay_app)-> action = BillFromHomeFragmentDirections.actionBillFromHomeFragmentToBillInstructionsFragment(getString(R.string.menu_bill_gpay_app), Category.GPAY_APP)
            getString(R.string.menu_bill_gpay_USSD) -> action = BillFromHomeFragmentDirections.actionBillFromHomeFragmentToBillInstructionsFragment(getString(R.string.menu_bill_gpay_USSD), Category.GPAY_USSD)
            getString(R.string.menu_bill_upay_app) -> action = BillFromHomeFragmentDirections.actionBillFromHomeFragmentToBillInstructionsFragment(getString(R.string.menu_bill_upay_app), Category.UPAY_APP)
            getString(R.string.menu_bill_ucash_USSD) -> action = BillFromHomeFragmentDirections.actionBillFromHomeFragmentToBillInstructionsFragment(getString(R.string.menu_bill_ucash_USSD), Category.UCASH_USSD)
        }
        if(action != null)
            findNavController().navigate(action)
    }
}