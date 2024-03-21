package com.galib.natorepbs2.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.galib.natorepbs2.NPBS2Application
import com.galib.natorepbs2.R
import com.galib.natorepbs2.models.ComplainCentre
import com.galib.natorepbs2.utils.OutLinedButtonWithAction
import com.galib.natorepbs2.utils.Utility
import com.galib.natorepbs2.viewmodel.ComplainCentreViewModel
import com.galib.natorepbs2.viewmodel.ComplainCentreViewModelFactory

class ComplainCentreFragment : Fragment() {
    private val complainCentreViewModel: ComplainCentreViewModel by viewModels {
        ComplainCentreViewModelFactory((activity?.application as NPBS2Application).repository)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                ComplainCentreUI(LocalContext.current)
            }
        }
    }

    @Composable
    fun ComplainCentreUI(context: Context) {
        val allComplainCentre: List<ComplainCentre>?  by complainCentreViewModel.allComplainCentre.observeAsState()
        ComplainCentreList(context, allComplainCentre)
    }

    @Composable
    fun ComplainCentreList(context: Context, complainCentreList: List<ComplainCentre>?) {
        if(complainCentreList.isNullOrEmpty()){
            Text("No Complain Centre found")
        } else{
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            )  {
                items(complainCentreList.size) { id ->
                    ItemComplainCentre(context, complainCentreList[id])
                }
            }
        }
    }
    @Composable
    fun ItemComplainCentre(context: Context, complainCentre: ComplainCentre) {
        ElevatedCard(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            colors = CardDefaults.cardColors(
                containerColor = colorResource(R.color.item_background),
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp
            ),
            shape = CutCornerShape(10.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp, horizontal = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = complainCentre.name,
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.W600,
                    color = Color.Black,
                )
                Text(
                    text = complainCentre.mobileNo,
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W300,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.size(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    OutLinedButtonWithAction(icon = Icons.Outlined.Call){
                        Utility.makeCall(context, complainCentre.mobileNo)
                    }
                    if(complainCentre.telephoneNo.isNotBlank()){
                        OutLinedButtonWithAction(icon = Icons.Outlined.Phone){
                            Utility.makeCall(context, complainCentre.telephoneNo)
                        }
                    }
                    OutLinedButtonWithAction(icon = Icons.Outlined.Email){
                        Utility.sendEmail(context, complainCentre.email)
                    }
                    OutLinedButtonWithAction(icon = Icons.Outlined.Place){
                        Utility.openMap(context, complainCentre.latitude, complainCentre.longitude)
                    }

                }
            }
        }
    }
}