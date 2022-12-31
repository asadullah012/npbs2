package com.galib.natorepbs2.customui.carouselview

import android.widget.ImageView

interface ImageListener {
    fun setImageForPosition(position: Int, imageView: ImageView)
}