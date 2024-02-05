package com.galib.natorepbs2.utils

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.galib.natorepbs2.R

@Composable
fun PersonCard(context: Context, name:String, designation:String, skills:String, mobile:String, email:String) {
    ConstraintLayout {
        val (title, description) = createRefs()
        Box(
            modifier = Modifier
                .constrainAs(description) {
                    top.linkTo(title.top, margin = 16.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
        ) {
            OutlinedCard(
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.onBackground)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Spacer(modifier = Modifier.size(width = 150.dp, height = 0.dp))
                        Column {
                            Text(
                                text = name,
                                textAlign = TextAlign.Center,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.W600,
                                color = Color.Black
                            )
                            Text(
                                text = designation,
                                textAlign = TextAlign.Center,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.W300,
                                color = Color.Black
                            )
                            Spacer(modifier = Modifier.size(10.dp))
                            Text(
                                text = skills,
                                textAlign = TextAlign.Start,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.W300,
                                color = Color.Black
                            )
                            Spacer(modifier = Modifier.size(10.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                OutLinedButtonWithAction(icon = Icons.Outlined.Call){
                                    Utility.makeCall(context, mobile)
                                }
                                OutLinedButtonWithAction(icon = Icons.Outlined.Email){
                                    Utility.sendEmail(context, email)
                                }
                                OutLinedButtonWithAction(icon = Icons.Filled.Favorite){

                                }
                            }
                        }

                    }
                }
            }
        }
        Box(
            modifier = Modifier
                .padding(start = 28.dp)
                .constrainAs(title) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
        ) {
            Image(
                painter = painterResource(R.drawable.person),
                contentDescription = "avatar",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(percent = 50))
                    .border(2.dp, Color.Gray, CircleShape)
            )
        }
    }

}

@Composable
fun OutLinedButtonWithAction(icon: ImageVector,  action:() -> Unit) {
    FilledTonalIconButton(
        onClick = {
            action()
        },
        modifier = Modifier,
        enabled = true,
        shape = RoundedCornerShape(12.dp),
        colors = IconButtonDefaults.filledTonalIconButtonColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
            disabledContainerColor = MaterialTheme.colorScheme.primaryContainer,
            disabledContentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        Icon(
            imageVector = icon,
            contentDescription = "Lock"
        )
    }
}
