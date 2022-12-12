package com.galib.natorepbs2.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.galib.natorepbs2.R
import com.github.barteksc.pdfviewer.PDFView

class PDFViewerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_p_d_f_viewer, container, false)
        var pdfViewer = root.findViewById<PDFView>(R.id.pdfViewer)
        pdfViewer.fromAsset("citizen_charter.pdf").load()
        return root
    }
}