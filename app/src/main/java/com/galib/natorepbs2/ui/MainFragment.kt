package com.galib.natorepbs2.ui

import android.graphics.BitmapFactory
import android.os.Bundle
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
import com.galib.natorepbs2.databinding.FragmentMainBinding
import java.io.File

class MainFragment : Fragment(), MenuOnClickListener {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding =
            DataBindingUtil.inflate<FragmentMainBinding>(inflater, R.layout.fragment_main, container, false)
        binding.title = getString(R.string.title)
        binding.bannerContentDescription = getString(R.string.banner_content_description)
        binding.adapter = MenuAdapter(requireContext(),this, getMenuList())

        val carouselView = binding.root.findViewById<CarouselView>(R.id.bannerCarousel)
        setBannerImages(carouselView)

        binding.lifecycleOwner = activity
        return binding.root
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

    private fun getMenuList(): MutableList<String> {
        val list : MutableList<String> = ArrayList()
        list.add(getString(R.string.menu_about_us))
        list.add(getString(R.string.menu_our_services))
        list.add(getString(R.string.menu_websites))
        list.add(getString(R.string.menu_social_media))
        list.add(getString(R.string.menu_related_apps))
        list.add(getString(R.string.menu_opinion_complain))
        list.add(getString(R.string.menu_notice_tender))
        list.add(getString(R.string.menu_communication))
        list.add(getString(R.string.menu_awareness))
        return list
    }

    override fun menuOnClick(v: View, menuText: String) {
        when (menuText) {
            getString(R.string.menu_about_us) -> findNavController(v).navigate(R.id.action_main_to_aboutUsFragment)
            getString(R.string.menu_our_services) -> findNavController(v).navigate(R.id.action_main_to_ourServicesFragment)
            getString(R.string.menu_websites) -> findNavController(v).navigate(R.id.action_main_to_websitesFragment)
            getString(R.string.menu_social_media) -> findNavController(v).navigate(R.id.action_main_to_socialMediaFragment)
            getString(R.string.menu_opinion_complain) -> findNavController(v).navigate(R.id.action_main_to_opinionComplainFragment)
            getString(R.string.menu_related_apps) -> findNavController(v).navigate(R.id.action_main_to_relatedAppsFragment)
            getString(R.string.menu_notice_tender) -> findNavController(v).navigate(R.id.action_main_to_noticeTenderFragment)
            getString(R.string.menu_communication) -> findNavController(v).navigate(R.id.action_main_to_communicationFragment)
            getString(R.string.menu_awareness) -> findNavController(v).navigate(R.id.action_main_to_awarenessFragment)
        }
    }
}