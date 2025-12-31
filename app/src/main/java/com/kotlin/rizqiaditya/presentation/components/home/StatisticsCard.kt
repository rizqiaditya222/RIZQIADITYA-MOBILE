package com.kotlin.rizqiaditya.presentation.components.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kotlin.rizqiaditya.ui.theme.MyBaseGray
import com.kotlin.rizqiaditya.ui.theme.MyDarkGray
import com.kotlin.rizqiaditya.ui.theme.MyWhite

@Composable
fun StatisticsCard(
    TrafficAmount: Int = 54,
    CommentsAmount: Int = 54,
    MessagesAmount: Int = 54,
) {
    Box(
        modifier = Modifier
            .padding(vertical = 16.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(color = MyBaseGray)
            .height(128.dp)
    ){
        Row (modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column (modifier = Modifier,
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center) {
                Text("Traffic", style = MaterialTheme.typography.titleMedium, color = MyDarkGray)
                Spacer(Modifier.height(8.dp))
                Text("$TrafficAmount", style = MaterialTheme.typography.displaySmall.copy(fontSize = 42.sp), color = MyWhite)
            }

            Box (modifier = Modifier
                .padding(vertical = 8.dp)
                .fillMaxHeight()
                .width(0.5.dp)
                .background(color = MyDarkGray))

            Column (modifier = Modifier,
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center) {
                Text("Comments", style = MaterialTheme.typography.titleMedium, color = MyDarkGray)
                Spacer(Modifier.height(8.dp))
                Text("$CommentsAmount", style = MaterialTheme.typography.displaySmall.copy(fontSize = 42.sp), color = MyWhite)
            }

            Box (modifier = Modifier
                .padding(vertical = 8.dp)
                .fillMaxHeight()
                .width(0.5.dp)
                .background(color = MyDarkGray))

            Column (modifier = Modifier,
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center) {
                Text("Messages", style = MaterialTheme.typography.titleMedium, color = MyDarkGray)
                Spacer(Modifier.height(8.dp))
                Text("$MessagesAmount", style = MaterialTheme.typography.displaySmall.copy(fontSize = 42.sp), color = MyWhite)
            }
        }
    }
}