package com.kotlin.rizqiaditya.presentation.components.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kotlin.rizqiaditya.ui.theme.MyBaseGray
import com.kotlin.rizqiaditya.ui.theme.MyDarkGray
import com.kotlin.rizqiaditya.ui.theme.MyWhite

@Composable
fun MessageCard(
    Content: String,
    Time: String,
    SpacerSize: Int = 8
) {
    Column {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .fillMaxWidth()
                    .background(color = MyBaseGray)
                    .padding(horizontal = 16.dp, vertical = 16.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start) {
                    Text("$Content", style = MaterialTheme.typography.bodyLarge, color = MyWhite, modifier = Modifier.weight(1f))
                    Spacer(Modifier.width(8.dp))
                    Text(Time,
                        style = MaterialTheme.typography.labelLarge,
                        color = MyDarkGray,
                        textAlign = TextAlign.End,
                        modifier = Modifier
                            .width(32.dp))
                }
            }
            Spacer(modifier = Modifier.height(SpacerSize.dp))
        }
    }