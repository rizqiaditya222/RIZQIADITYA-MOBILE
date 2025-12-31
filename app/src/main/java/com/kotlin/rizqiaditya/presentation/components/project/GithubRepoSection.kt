package com.kotlin.rizqiaditya.presentation.components.project

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kotlin.rizqiaditya.presentation.components.common.MyOutlinedTextField
import com.kotlin.rizqiaditya.ui.theme.MyDarkGray

@Composable
fun GithubRepoSection(
    title: String,
    isOptional: Boolean,
    nameValue: String,
    onNameChange: (String) -> Unit,
    urlValue: String,
    onUrlChange: (String) -> Unit
) {
    Column {
        Text(
            text = if (isOptional) "$title (Opsional)" else title,
            style = MaterialTheme.typography.labelLarge,
            color = MyDarkGray
        )

        Spacer(Modifier.height(8.dp))

        MyOutlinedTextField(
            value = nameValue,
            onValueChange = onNameChange,
            placeholder = "Repository Name",
            singleLine = true
        )

        Spacer(Modifier.height(8.dp))

        MyOutlinedTextField(
            value = urlValue,
            onValueChange = onUrlChange,
            placeholder = "https://github.com/username/repo",
            singleLine = true
        )
    }
}
