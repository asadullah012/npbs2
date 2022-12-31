package com.galib.natorepbs2.customui.carouselview

import android.view.View

interface ViewListener {
    fun setViewForPosition(position: Int): View?
}