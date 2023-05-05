package com.galib.natorepbs2.settings

import android.graphics.drawable.Drawable
import android.view.View

data class Setting(
    val name: String,
    val icon: Drawable?,
    val onClickListener: View.OnClickListener?
)