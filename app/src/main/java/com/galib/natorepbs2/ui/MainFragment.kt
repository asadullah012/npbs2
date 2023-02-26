package com.galib.natorepbs2.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.galib.natorepbs2.NPBS2Application
import com.galib.natorepbs2.R
import com.galib.natorepbs2.customui.carouselview.CarouselView
import com.galib.natorepbs2.customui.carouselview.ImageListener
import com.galib.natorepbs2.databinding.FragmentMainBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext


class MainFragment : Fragment(), CoroutineScope, MenuOnClickListener {
    private var job: Job = Job()
    override val coroutineContext: CoroutineContext = Dispatchers.Main + job

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

        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    private fun setBannerImages(carouselView: CarouselView){
        val sampleImages = getBannerImages()
        lateinit var imageListener: ImageListener
        if(sampleImages.isEmpty()){
            carouselView.pageCount = 1
            imageListener = object : ImageListener {
                override fun setImageForPosition(position: Int, imageView: ImageView) {
//                    val src = BitmapFactory.decodeResource(resources, R.drawable.npbs2)
//                    val dr = RoundedBitmapDrawableFactory.create(resources, src)
//                    dr.cornerRadius = src.height/10.0F
                    imageView.setImageDrawable(resources.getDrawable(R.drawable.npbs2, requireContext().theme))
                }
            }
        } else {
            carouselView.pageCount = sampleImages.size
            imageListener = object : ImageListener {
                override fun setImageForPosition(position: Int, imageView: ImageView) {
//                    Utility.loadImageInPicasso(sampleImages, position,imageView, resources)
                        Picasso.get()
                            .load(sampleImages[position])
                            .into(imageView)
                        }
            }
        }
        carouselView.setImageListener(imageListener)
    }

    private fun getBannerImages(): List<String> {
        val apps : NPBS2Application = activity?.application as NPBS2Application
        return apps.repository.getBannerUrls()
    }

    private fun getMenuList(): MutableList<String> {
        val list : MutableList<String> = ArrayList()
        list.add(getString(R.string.menu_about_us))
        list.add(getString(R.string.menu_our_services))
        list.add(getString(R.string.menu_electricity_bill))
        list.add(getString(R.string.menu_offices))
        list.add(getString(R.string.menu_websites))
        list.add(getString(R.string.menu_notice_tender))
        list.add(getString(R.string.menu_social_media))
        list.add(getString(R.string.menu_related_apps))
        list.add(getString(R.string.menu_opinion_complain))
        list.add(getString(R.string.menu_communication))
        list.add(getString(R.string.menu_awareness))
        list.add(getString(R.string.menu_other_official_contacts))
        return list
    }

    override fun menuOnClick(menuText: String) {
        when (menuText) {
            getString(R.string.menu_about_us) -> findNavController().navigate(R.id.action_main_to_aboutUsFragment)
            getString(R.string.menu_our_services) -> findNavController().navigate(R.id.action_main_to_ourServicesFragment)
            getString(R.string.menu_electricity_bill) -> findNavController().navigate(R.id.action_mainFragment_to_electricityBillFragment)
            getString(R.string.menu_offices) -> findNavController().navigate(R.id.action_mainFragment_to_officesFragment)
            getString(R.string.menu_websites) -> findNavController().navigate(R.id.action_main_to_websitesFragment)
            getString(R.string.menu_social_media) -> findNavController().navigate(R.id.action_main_to_socialMediaFragment)
            getString(R.string.menu_opinion_complain) -> findNavController().navigate(R.id.action_main_to_opinionComplainFragment)
            getString(R.string.menu_related_apps) -> findNavController().navigate(R.id.action_main_to_relatedAppsFragment)
            getString(R.string.menu_notice_tender) -> findNavController().navigate(R.id.action_main_to_noticeTenderFragment)
            getString(R.string.menu_communication) -> findNavController().navigate(R.id.action_main_to_communicationFragment)
            getString(R.string.menu_awareness) -> findNavController().navigate(R.id.action_main_to_awarenessFragment)
            getString(R.string.menu_other_official_contacts) -> findNavController().navigate(R.id.action_mainFragment_to_otherOfficeContactsFragment)
        }
    }
}