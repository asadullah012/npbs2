package com.galib.natorepbs2.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.galib.natorepbs2.NPBS2Application
import com.galib.natorepbs2.R
import com.galib.natorepbs2.constants.Category
import com.galib.natorepbs2.databinding.FragmentAtAGlanceBinding
import com.galib.natorepbs2.db.Information
import com.galib.natorepbs2.viewmodel.*

class AtAGlanceFragment : Fragment() {
    private val informationViewModel: InformationViewModel by viewModels {
        InformationViewModelFactory((activity?.application as NPBS2Application).repository)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentAtAGlanceBinding>(
            inflater,
            R.layout.fragment_at_a_glance,
            container,
            false
        )
        binding.pageTitle = getString(R.string.menu_at_a_glance)
        binding.lifecycleOwner = activity
        val adapter = InformationAdapter(InformationAdapter.WordDiff())
        informationViewModel!!.getInformationByCategory(Category.atAGlance).observe(
            viewLifecycleOwner
        ) { list: List<Information?> -> adapter.submitList(list) }
        informationViewModel!!.month.observe(viewLifecycleOwner) { information: Information ->
            binding.month = information.title
        }
        binding.adapter = adapter
        return binding.root
    }
}