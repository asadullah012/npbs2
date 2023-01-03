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
import kotlin.math.ceil
import kotlin.math.roundToInt

class BillCalculatorFragment : Fragment() {
    var slabLifeline = 0F
    var slab1 = 0F
    var slab2 = 0F
    var slab3 = 0F
    var slab4 = 0F
    var slab5 = 0F
    var slab6 = 0F
    var energyCharge = 0F
    var nitBill = 0F
    var demandCharge = 30F
    var vat = 0F
    val meterRent = 10F
    var totalBill = 0F
    var LPC = 0F
    var totalBillWithLPC = 0F
    var KWH = 0
    var demand = 1
    lateinit var binding: FragmentBillCalculatorBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
            try{
                KWH = text.toString().toInt()
            } catch (e: NumberFormatException ){
                e.printStackTrace()
                KWH = 0
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
        var kwh = KWH
        if(kwh <= 50){
            slabLifeline = kwh * 3.75F
        } else {
            if(kwh > 75){
                slab1 = 75 * 4.19F
                kwh -= 75
            } else{
                slab1 = kwh * 4.19F
                kwh = 0
            }
            if(kwh > 125){
                slab2 = 125 * 5.72F
                kwh -= 125
            } else{
                slab2 = kwh * 5.72F
                kwh = 0
            }
            if(kwh > 100){
                slab3 = 100 * 6F
                kwh -= 100
            } else{
                slab3 = kwh * 6F
                kwh = 0
            }
            if(kwh > 100){
                slab4 = 100 * 6.34F
                kwh -= 100
            } else{
                slab4 = kwh * 6.34F
                kwh = 0
            }
            if(kwh > 200){
                slab5 = 200 * 9.94F
                kwh -= 200
            } else{
                slab5 = kwh * 9.94F
                kwh = 0
            }
            slab6 = kwh * 11.46F
        }
        energyCharge = slabLifeline + slab1 + slab2 + slab3 + slab4 + slab5 + slab6
        demandCharge = demand*30F * ( if(isExtraConsumption(KWH, demand)) 3 else 1 )
        nitBill = (energyCharge + demandCharge)
        vat = nitBill * 0.05F
        totalBill = nitBill + vat + meterRent
        LPC = vat
        totalBillWithLPC = totalBill + LPC
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
        binding.lpcOut = LPC.roundToInt()
        binding.totalBillWithLPCOut = totalBillWithLPC.roundToInt()
    }

    private fun resetVariables(){
        slabLifeline = 0F
        slab1 = 0F
        slab2 = 0F
        slab3 = 0F
        slab4 = 0F
        slab5 = 0F
        slab6 = 0F
        energyCharge = 0F
        nitBill = 0F
        demandCharge = 30F
        vat = 0F
        totalBill = 0F
        LPC = 0F
        totalBillWithLPC = 0F
    }

    fun round2Decimal(number: Float): Float{
        return (number * 100.0).roundToInt() / 100.0F
    }

    fun isExtraConsumption(kwh: Int, demand: Int) : Boolean{
        if (demand > 10) return false
        val consumptionLimit = arrayOf(162,281,421,562,648,778,907,1037,1166,1296)
        return kwh > consumptionLimit[demand-1]
    }
}