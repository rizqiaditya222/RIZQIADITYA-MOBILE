package com.kotlin.rizqiaditya.presentation.components.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.kotlin.rizqiaditya.ui.theme.MyBlack
import com.kotlin.rizqiaditya.ui.theme.MyWhite

@Composable
fun ActionButton(
    icon: ImageVector,
    contentDescription: String? = null,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .size(28.dp)
            .clip(RoundedCornerShape(18.dp))
            .background(color = MyWhite)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = MyBlack,
            modifier = Modifier.size(20.dp)
        )
    }
}
