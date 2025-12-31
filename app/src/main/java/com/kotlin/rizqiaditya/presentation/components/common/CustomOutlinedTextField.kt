package com.kotlin.rizqiaditya.presentation.components.common

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kotlin.rizqiaditya.ui.theme.MyBaseGray
import com.kotlin.rizqiaditya.ui.theme.MyDarkGray
import com.kotlin.rizqiaditya.ui.theme.MyLightGray
import com.kotlin.rizqiaditya.ui.theme.MyWhite

@Composable
fun MyOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    singleLine: Boolean = true,
    maxLines: Int = 1
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 0.4.dp,
                color = MyDarkGray,
                shape = RoundedCornerShape(8.dp)
            )
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(
                    text = placeholder,
                    color = MyWhite.copy(alpha = 0.3f)
                )
            },
            singleLine = singleLine,
            maxLines = maxLines,
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MyBaseGray,
                unfocusedContainerColor = MyBaseGray,
                disabledContainerColor = MyBaseGray,

                focusedBorderColor = MyLightGray,
                unfocusedBorderColor = MyLightGray.copy(alpha = 0.7f),
                disabledBorderColor = MyLightGray.copy(alpha = 0.4f),

                focusedTextColor = MyWhite,
                unfocusedTextColor = MyWhite,
                cursorColor = MyWhite
            )
        )
    }
}
