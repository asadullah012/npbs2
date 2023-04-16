package com.galib.natorepbs2.ui

import android.os.Bundle
import com.galib.natorepbs2.logger.LogUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.galib.natorepbs2.R
import com.github.barteksc.pdfviewer.PDFView
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody
import okio.Buffer
import okio.BufferedSink
import okio.Okio
import java.io.File
import java.io.IOException

class PDFViewerFragment : Fragment(), CoroutineScope{
    private val args: PDFViewerFragmentArgs by navArgs()
    private val job = Job()
    override val coroutineContext = Dispatchers.Main + job
    private lateinit var pdfViewer: PDFView
    private lateinit var progressBar: ProgressBar
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_p_d_f_viewer, container, false)
        val titleTextView = root.findViewById<TextView>(R.id.titleTextView)
        titleTextView.text = args.title
        progressBar = ProgressBar(context)
        pdfViewer = root.findViewById(R.id.pdfViewer)
        val file = File(requireContext().filesDir, args.fileName + ".pdf")
        if(file.exists()) {
            pdfViewer.fromFile(file).load()
            LogUtils.d("PDFView", "onCreateView: ${file.absolutePath} exist")
        }
        else {
            if(args.url != null)
                downloadAndLoad(args.url!!, file)
            LogUtils.e("PDFView", "onCreateView: ${file.absolutePath} does not exist")
        }
        return root
    }

    private fun downloadAndLoad(url: String, file: File) {
        progressBar.visibility = View.VISIBLE
        launch {
            withContext(Dispatchers.IO) {
                try {
                    val client = OkHttpClient()
                    val request = Request.Builder().url(url).build()
                    val response: Response = client.newCall(request).execute()
                    val body: ResponseBody? = response.body()
                    val contentLength = body!!.contentLength()
                    val source = body.source()

                    val sink: BufferedSink = Okio.buffer(Okio.sink(file))
                    val sinkBuffer: Buffer = sink.buffer()

                    var totalBytesRead: Long = 0
                    val bufferSize : Long = 8 * 1024
                    var bytesRead : Long = source.read(sinkBuffer, bufferSize)
                    while(bytesRead != -1L){
                        sink.emit()
                        totalBytesRead += bytesRead
                        val progress = (totalBytesRead * 100 / contentLength).toInt()
                        progressBar.progress = progress
                        bytesRead = source.read(sinkBuffer, bufferSize)
                    }
                    sink.flush()
                    sink.close()
                    source.close()
                } catch (e : IOException){
                    e.printStackTrace()
                }
            }
            pdfViewer.fromFile(file).load()
            LogUtils.d("PDFView", "downloadAndLoad: ${file.absolutePath} done")
        }
        progressBar.visibility = View.GONE
    }
}