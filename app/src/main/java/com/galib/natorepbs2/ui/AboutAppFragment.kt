package com.galib.natorepbs2.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import com.galib.natorepbs2.utils.PageIndicator
import com.galib.natorepbs2.utils.PersonCard

class AboutAppFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                AboutAppUI()
            }
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Preview
    @Composable
    fun AboutAppUI() {
        val pagerState = rememberPagerState(pageCount = {
            5
        })


        HorizontalPager(state = pagerState) { page ->
            when (page) {
                1 -> {
                    DeveloperPage()
                }
                2 -> {
                    DeveloperPage()
                }
                else -> {
                    DeveloperPage()
                }
            }
        }
        PageIndicator(
            numberOfPages = pagerState.pageCount,
            selectedPage = pagerState.currentPage,
            defaultRadius = 15.dp,
            selectedLength = 30.dp,
            space = 10.dp,
        )
    }
    @Composable
    fun DeveloperPage(){
        Column (
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.Center){
            Text(text = "App Developer", fontSize = 32.sp, modifier = Modifier.align(Alignment.CenterHorizontally))
            Spacer(modifier = Modifier.height(10.dp))
            PersonCard(context = LocalContext.current, name = "Asadullahil Galib", designation = "AGM(IT-E&I) at Natore PBS-2",
                skills = "Android • Kotlin • Java • Mobile Application Development • Problem Solving",
                mobile = "+8801737607415", email= "galib.reb.pbs@gmail.com")

        }
    }
}