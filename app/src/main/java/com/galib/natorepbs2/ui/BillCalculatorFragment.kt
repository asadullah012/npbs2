package com.galib.natorepbs2.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.galib.natorepbs2.R
import com.galib.natorepbs2.databinding.FragmentBillCalculatorBinding
import com.galib.natorepbs2.utils.Utility
import kotlin.math.ceil
import kotlin.math.roundToInt

class BillCalculatorFragment : Fragment() {
    private var slabLifeline = 0.0
    private var slab1 = 0.0
    private var slab2 = 0.0
    private var slab3 = 0.0
    private var slab4 = 0.0
    private var slab5 = 0.0
    private var slab6 = 0.0
    private var energyCharge = 0.0
    private var nitBill = 0.0
    private var demandCharge = 35.0
    private var vat = 0.0
    private val meterRent = 10.0
    private var totalBill = 0.0
    var lpc = 0.0
    private var totalBillWithLPC = 0.0
    var kWH = 0
    private var demand = 1
    lateinit var binding: FragmentBillCalculatorBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_bill_calculator,
            container,
            false
        )
        binding.pageTitle = getString(R.string.menu_electricity_bill_calculator)
        binding.root.findViewById<EditText>(R.id.KWHInput).doOnTextChanged { text, _, _, _ ->
            Log.d("Bill Calculator", "onCreateView: KWHInput $text")
            resetVariables()
            kWH = try{
                text.toString().toInt()
            } catch (e: NumberFormatException ){
                e.printStackTrace()
                0
            }
            updateBill()
        }
        binding.root.findViewById<EditText>(R.id.demandInput).doOnTextChanged { text, _, _, _ ->
            Log.d("Bill Calculator", "onCreateView: demandInput $text")
            resetVariables()
            try{
                demand = ceil(text.toString().toFloat()).toInt()
                if(demand == 0)
                    demand = 1
            } catch (e: NumberFormatException ){
                e.printStackTrace()
                demand = 1
            }
            updateBill()
        }
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    private fun updateBill() {
        var kwh = kWH
        if(kwh <= 50){
            slabLifeline = kwh * Utility.getElectricityRate(requireContext(),"LT-A", 0)
        } else {
            if(kwh > 75){
                slab1 = 75 * Utility.getElectricityRate(requireContext(),"LT-A", 1)
                kwh -= 75
            } else{
                slab1 = kwh * Utility.getElectricityRate(requireContext(),"LT-A", 1)
                kwh = 0
            }
            if(kwh > 125){
                slab2 = 125 * Utility.getElectricityRate(requireContext(),"LT-A", 2)
                kwh -= 125
            } else{
                slab2 = kwh * Utility.getElectricityRate(requireContext(),"LT-A", 2)
                kwh = 0
            }
            if(kwh > 100){
                slab3 = 100 * Utility.getElectricityRate(requireContext(),"LT-A", 3)
                kwh -= 100
            } else{
                slab3 = kwh * Utility.getElectricityRate(requireContext(),"LT-A", 3)
                kwh = 0
            }
            if(kwh > 100){
                slab4 = 100 * Utility.getElectricityRate(requireContext(),"LT-A", 4)
                kwh -= 100
            } else{
                slab4 = kwh * Utility.getElectricityRate(requireContext(),"LT-A", 4)
                kwh = 0
            }
            if(kwh > 200){
                slab5 = 200 * Utility.getElectricityRate(requireContext(),"LT-A", 5)
                kwh -= 200
            } else{
                slab5 = kwh * Utility.getElectricityRate(requireContext(),"LT-A", 5)
                kwh = 0
            }
            slab6 = kwh * Utility.getElectricityRate(requireContext(),"LT-A", 6)
        }
        energyCharge = slabLifeline + slab1 + slab2 + slab3 + slab4 + slab5 + slab6
        demandCharge = demand*Utility.getDemandCharge(requireContext(), "LT-A") * ( if(isExtraConsumption(kWH, demand)) 3 else 1 )
        nitBill = (energyCharge + demandCharge)
        vat = nitBill * 0.05F
        totalBill = nitBill + vat + meterRent
        lpc = vat
        totalBillWithLPC = totalBill + lpc
        updateUI()
    }

    private fun updateUI(){
        binding.slab0Out = round2Decimal(slabLifeline)
        binding.slab1Out = round2Decimal(slab1)
        binding.slab2Out = round2Decimal(slab2)
        binding.slab3Out = round2Decimal(slab3)
        binding.slab4Out = round2Decimal(slab4)
        binding.slab5Out = round2Decimal(slab5)
        binding.slab6Out = round2Decimal(slab6)
        binding.energyChargeOut = energyCharge.roundToInt()
        binding.nitBillOut = nitBill.roundToInt()
        binding.demandChargeOut = demandCharge.roundToInt()
        binding.vatOut = vat.roundToInt()
        binding.meterRentOut = meterRent.roundToInt()
        binding.totalBillOut = totalBill.roundToInt()
        binding.lpcOut = lpc.roundToInt()
        binding.totalBillWithLPCOut = totalBillWithLPC.roundToInt()
    }

    private fun round2Decimal(num: Double): Float {
        return (num * 100.0).roundToInt() / 100.0F
    }

    private fun resetVariables(){
        slabLifeline = 0.0
        slab1 = 0.0
        slab2 = 0.0
        slab3 = 0.0
        slab4 = 0.0
        slab5 = 0.0
        slab6 = 0.0
        energyCharge = 0.0
        nitBill = 0.0
        demandCharge = Utility.getDemandCharge(requireContext(), "LT-A")
        vat = 0.0
        totalBill = 0.0
        lpc = 0.0
        totalBillWithLPC = 0.0
    }

    private fun isExtraConsumption(kwh: Int, demand: Int) : Boolean{
        if (demand > 10) return false
        val consumptionLimit = arrayOf(162,281,421,562,648,778,907,1037,1166,1296)
        return kwh > consumptionLimit[demand-1]
    }
}