package com.kotlin.rizqiaditya.presentation.components.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kotlin.rizqiaditya.ui.theme.MyBlack
import com.kotlin.rizqiaditya.ui.theme.MyWhite

@Composable
fun TopBar(
    title: String,
    leftIcon: ImageVector?,
    onLeftClick: () -> Unit,
    rightIcon: ImageVector?,
    onRightClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MyBlack)
            .padding(top = 16.dp, bottom = 24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        if (leftIcon!=null) ActionButton(
            icon = leftIcon,
            contentDescription = "Left Action",
            onClick = onLeftClick
        )

        Text(
            text = title,
            style = MaterialTheme.typography.displaySmall,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp),
            textAlign = TextAlign.Center,
            color = MyWhite
        )

        if (rightIcon!=null)ActionButton(
            icon = rightIcon,
            contentDescription = "Right Action",
            onClick = onRightClick
        )
    }
}


