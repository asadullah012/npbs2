package com.galib.natorepbs2.ui

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.galib.natorepbs2.R
import com.galib.natorepbs2.constants.URLs
import com.github.barteksc.pdfviewer.PDFView
import java.io.File
import java.io.IOException

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