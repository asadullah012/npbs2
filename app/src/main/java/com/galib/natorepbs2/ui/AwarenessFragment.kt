package com.galib.natorepbs2.ui

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.galib.natorepbs2.R
import com.galib.natorepbs2.carouselview.CarouselView
import com.galib.natorepbs2.carouselview.ImageListener
import com.galib.natorepbs2.databinding.FragmentAwarenessBinding


class AwarenessFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DataBindingUtil.inflate<FragmentAwarenessBinding>(
            inflater,
            R.layout.fragment_awareness,
            container,
            false
        )
        binding.pageTitle = getString(R.string.menu_awareness)
        binding.fragment = this
        binding.lifecycleOwner = activity
        val carouselView = binding.root.findViewById<CarouselView>(R.id.carouselView)
        val sampleImages = arrayOf(R.drawable.awareness1, R.drawable.awareness2, R.drawable.awareness3)
        carouselView.pageCount = sampleImages.size
        val imageListener = object : ImageListener {
            override fun setImageForPosition(position: Int, imageView: ImageView) {
//                imageView.setImageResource(sampleImages[position])
                val src = BitmapFactory.decodeResource(resources, sampleImages[position])
                val dr = RoundedBitmapDrawableFactory.create(resources, src)
                dr.cornerRadius = 20.0f
                imageView.setImageDrawable(dr)
            }
        }
        carouselView.setImageListener(imageListener)
        return binding.root
    }
}