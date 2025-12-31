package com.kotlin.rizqiaditya.presentation.components.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.kotlin.rizqiaditya.ui.theme.MyDarkGray
import com.kotlin.rizqiaditya.ui.theme.MyWhite
import com.kotlin.rizqiaditya.ui.theme.RizqiadityaTheme

@Composable
fun LoadingDialog(
    modifier: Modifier = Modifier,
    message: String = "Wait a moment…",
    onDismissRequest: (() -> Unit)? = null
) {
    Dialog(onDismissRequest = { onDismissRequest?.invoke() ?: Unit }) {
        Surface(
            modifier = modifier,
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.primary,
            tonalElevation = 8.dp
        ) {
            Column(
                modifier = Modifier
                    .widthIn(min = 200.dp)
                    .then(Modifier),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Spacer(modifier = Modifier.height(24.dp))
                CircularProgressIndicator(color = MyWhite, strokeWidth = 4.dp, modifier = Modifier.size(42.dp))
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = message, color = MyDarkGray, style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold))
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Preview
@Composable
fun LoadingDialogPreview() {
    RizqiadityaTheme {
        LoadingDialog()
    }
}