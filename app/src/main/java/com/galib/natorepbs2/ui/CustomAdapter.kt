package com.galib.natorepbs2.ui

import android.content.ClipData
import android.content.ClipDescription
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.galib.natorepbs2.R


interface CustomListener {
    fun setEmptyList(visibility: Int, recyclerView: Int, emptyTextView: Int)
}

class CustomAdapter(private var list: List<String>, private val listener: CustomListener?)
    : RecyclerView.Adapter<CustomAdapter.CustomViewHolder?>(), View.OnLongClickListener {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return CustomViewHolder(inflater, parent)
    }

    override fun getItemCount(): Int = list.size

    fun updateList(list: MutableList<String>) {
        this.list = list
    }

    fun getList(): MutableList<String> = this.list.toMutableList()

    override fun onLongClick(v: View?): Boolean{
        Log.d("TAG", "onLongClick: ")
        if(v == null) return false
        val item = ClipData.Item(v.tag.toString() as CharSequence)
        val mimeTypes = arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN)
        val dragData = ClipData(
            v.tag.toString() as? CharSequence,
            mimeTypes, item)

        val myShadow = MyDragShadowBuilder(v)

        if (VERSION.SDK_INT >= VERSION_CODES.N) {
            v.startDragAndDrop(dragData, myShadow, null, 0)
        } else {
            v.startDrag(dragData, myShadow, null, 0)
        }
        return true
    }

    val dragInstance: DragListener?
        get() = if (listener != null) {
            DragListener(listener)
        } else {
            Log.e(javaClass::class.simpleName, "Listener not initialized")
            null
        }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.text?.text = list[position]
        holder.frameLayout?.tag = position
        holder.frameLayout?.setOnLongClickListener(this)
        holder.frameLayout?.setOnDragListener(DragListener(listener!!))
    }

    class CustomViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.item_fragment, parent, false)) {
        var text: TextView? = null
        var frameLayout: FrameLayout? = null

        init {
            text = itemView.findViewById(R.id.text)
            frameLayout = itemView.findViewById(R.id.frame_layout_item)
        }
    }
}

private class MyDragShadowBuilder(v: View) : View.DragShadowBuilder(v) {

    private val shadow = ColorDrawable(Color.LTGRAY)

    override fun onProvideShadowMetrics(size: Point, touch: Point) {
        val width: Int = view.width / 2
        val height: Int = view.height / 2
        shadow.setBounds(0, 0, width, height)
        size.set(width, height)
        touch.set(width / 2, height / 2)
    }
    override fun onDrawShadow(canvas: Canvas) {
        shadow.draw(canvas)
    }
}