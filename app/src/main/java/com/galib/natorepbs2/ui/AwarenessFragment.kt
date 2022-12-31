package com.galib.natorepbs2.ui

import android.app.Dialog
import android.graphics.Bitmap
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
import com.galib.natorepbs2.customui.carouselview.CarouselView
import com.galib.natorepbs2.customui.touchimageview.TouchImageView
import com.galib.natorepbs2.customui.carouselview.ImageClickListener
import com.galib.natorepbs2.customui.carouselview.ImageListener
import com.galib.natorepbs2.databinding.FragmentAwarenessBinding
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso


class AwarenessFragment : Fragment(), ImageClickListener {
    val sampleImages = arrayOf(R.drawable.awareness1, R.drawable.awareness2, R.drawable.awareness3)
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
        carouselView.pageCount = sampleImages.size
        carouselView.setImageClickListener(this)
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

    override fun onClick(position: Int) {
        val displayMetrics = resources.displayMetrics
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.popup_image)
        val imageView = dialog.findViewById<TouchImageView>(R.id.imageView)
        Picasso.get()
            .load(sampleImages[position])
            .into(imageView,  object: Callback {
                override fun onSuccess() {
                    val src = BitmapFactory.decodeResource(resources, sampleImages[position])
                    val ratio = src.height.toFloat()/src.width
                    val width = (displayMetrics.widthPixels*0.8).toInt()
                    val height = (width * ratio).toInt() //TODO if height is to much big, it may cause overflow
                    val scaled = Bitmap.createScaledBitmap(src, width,height, true)
                    val dr = RoundedBitmapDrawableFactory.create(resources, scaled)
                    dr.cornerRadius = 20.0F
                    imageView.setImageDrawable(dr)
                }

                override fun onError(e: java.lang.Exception?) {
                    e?.printStackTrace()
                }
            })
        imageView.adjustViewBounds = true
        dialog.show()
    }
}