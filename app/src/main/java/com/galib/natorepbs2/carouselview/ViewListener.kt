package com.galib.natorepbs2.carouselview

import android.view.View

interface ViewListener {
    fun setViewForPosition(position: Int): View?
}