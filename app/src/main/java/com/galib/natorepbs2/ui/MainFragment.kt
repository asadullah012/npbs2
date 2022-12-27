package com.galib.natorepbs2.ui

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import com.galib.natorepbs2.R
import com.galib.natorepbs2.carouselview.CarouselView
import com.galib.natorepbs2.carouselview.ImageListener
import com.galib.natorepbs2.databinding.FragmentMainV2Binding
import java.io.File

class MainFragment : Fragment(), MenuOnClickListener {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding =
            DataBindingUtil.inflate<FragmentMainV2Binding>(inflater, R.layout.fragment_main_v2, container, false)
        binding.title = getString(R.string.title)
        binding.about = getString(R.string.menu_about_us)
        binding.service = getString(R.string.menu_our_services)
        binding.awareness = getString(R.string.menu_awareness)
        binding.communication = getString(R.string.menu_communication)
        binding.opinionComplain = getString(R.string.menu_opinion_complain)
        binding.relatedApps = getString(R.string.menu_related_apps)
        binding.socialMedia = getString(R.string.menu_social_media)
        binding.website = getString(R.string.menu_websites)
        binding.noticeTender = getString(R.string.menu_notice_tender)
        binding.bannerContentDescription = getString(R.string.banner_content_description)
        val list : MutableList<String> = ArrayList()
        list.add(getString(R.string.menu_about_us))
        list.add(getString(R.string.menu_our_services))
        val menuAdpater = MenuAdapter(this)
        menuAdpater.submitList(list)
        binding.adapter = menuAdpater
        binding.lifecycleOwner = activity
        binding.fragment = this
        val carouselView = binding.root.findViewById<CarouselView>(R.id.bannerCarousel)
        setBannerImages(carouselView)
        return binding.root
    }

    fun onClick(v: View) {
        when (v.id) {
            R.id.aboutUsBtn -> {
                findNavController(v).navigate(R.id.action_main_to_aboutUsFragment)
                //            Navigation.createNavigateOnClickListener(R.id.action_main_to_aboutUsFragment, null);
            }
            R.id.serviceBtn -> {
                findNavController(v).navigate(R.id.action_main_to_ourServicesFragment)
            }
            R.id.websiteBtn -> {
                findNavController(v).navigate(R.id.action_main_to_websitesFragment)
            }
            R.id.social_media_btn -> {
                findNavController(v).navigate(R.id.action_main_to_socialMediaFragment)
            }
            R.id.opinion_complain_btn -> {
                findNavController(v).navigate(R.id.action_main_to_opinionComplainFragment)
            }
            R.id.related_apps_btn -> {
                findNavController(v).navigate(R.id.action_main_to_relatedAppsFragment)
            }
            R.id.notice_tender_btn -> {
                findNavController(v).navigate(R.id.action_main_to_noticeTenderFragment)
            }
            R.id.communication_btn -> {
                findNavController(v).navigate(R.id.action_main_to_communicationFragment)
            }
            R.id.awareness_btn -> {
                findNavController(v).navigate(R.id.action_main_to_awarenessFragment)
            }
        }
    }

    private fun setBannerImages(carouselView: CarouselView){
        val sampleImages = getBannerImages()
        lateinit var imageListener: ImageListener
        if(sampleImages == null || sampleImages.isEmpty()){
            carouselView.pageCount = 1
            imageListener = object : ImageListener {
                override fun setImageForPosition(position: Int, imageView: ImageView) {
                    imageView.setImageResource(R.drawable.npbs2)
                }
            }
        } else{
            carouselView.pageCount = sampleImages.size
            imageListener = object : ImageListener {
                override fun setImageForPosition(position: Int, imageView: ImageView) {
//                    imageView.setImageBitmap(BitmapFactory.decodeFile(sampleImages[position]))
                    val src = BitmapFactory.decodeFile(sampleImages[position])
                    val dr = RoundedBitmapDrawableFactory.create(resources, src)
                    dr.cornerRadius = src.height/10.0F
                    imageView.setImageDrawable(dr)
                }
            }
        }
        carouselView.setImageListener(imageListener)
    }
    private fun getBannerImages(): ArrayList<String>? {
        val dirPath: String = requireContext().filesDir.absolutePath + File.separator + "banners"
        val bannerDir = File(dirPath)
        if (!bannerDir.exists()) return null
        val bannerImages= ArrayList<String>()
        if(bannerDir.listFiles() != null){
            for(file in bannerDir.listFiles()!!){
                bannerImages.add(file.absolutePath)
            }
        }
        return bannerImages
    }

    override fun menuOnClick(menuText: String) {
        Log.d("MainFragment", "menuOnClick: $menuText")
    }
}