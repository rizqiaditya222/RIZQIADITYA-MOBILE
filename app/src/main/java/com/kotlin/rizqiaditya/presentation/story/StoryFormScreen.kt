package com.kotlin.rizqiaditya.presentation.story

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kotlin.rizqiaditya.presentation.components.common.ActionButton
import com.kotlin.rizqiaditya.presentation.components.common.LoadingDialog
import com.kotlin.rizqiaditya.presentation.components.common.MyOutlinedTextField
import com.kotlin.rizqiaditya.presentation.components.story.ImagePicker
import com.kotlin.rizqiaditya.presentation.components.common.MessageCard
import com.kotlin.rizqiaditya.ui.theme.MyBaseGray
import com.kotlin.rizqiaditya.ui.theme.MyBlack
import com.kotlin.rizqiaditya.ui.theme.MyDarkGray
import com.kotlin.rizqiaditya.ui.theme.MyWhite
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StoryFormScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    factory: ViewModelProvider.Factory,
    isEdit: Boolean = false,
    storyId: String = ""
) {
    val viewModel: StoryViewModel = viewModel(factory = factory)
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(isEdit, storyId) {
        if (isEdit && storyId.isNotBlank()) {
            viewModel.onEvent(StoryEvent.LoadForEdit(storyId))
        }
    }

    LaunchedEffect(uiState.createdStoryId) {
        if (!uiState.createdStoryId.isNullOrBlank()) {
            onBack()
        }
    }

    Scaffold(
        containerColor = MyBlack,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = if (isEdit) "Archived Story" else "Add Story",
                        style = MaterialTheme.typography.displaySmall,
                        color = MyWhite,
                        textAlign = TextAlign.Center
                    )
                },
                navigationIcon = {
                    ActionButton(
                        icon = Icons.Default.Add,
                        contentDescription = "Back",
                        onClick = { onBack() }
                    )
                },
                actions = {
                    ActionButton(
                        icon = Icons.Default.Add,
                        contentDescription = "Add",
                        onClick = { viewModel.onEvent(StoryEvent.Submit) }
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = MyBlack),
                modifier = Modifier.padding(horizontal = 20.dp)
            )
        }
    ) { innerPadding ->

        Column(
            modifier = modifier
                .fillMaxSize()
                .background(MyBlack)
                .padding(innerPadding)
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(216.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(MyBaseGray)
                    .border(
                        width = 0.5.dp,
                        color = MyDarkGray,
                        shape = RoundedCornerShape(12.dp)
                    )
            ) {
                ImagePicker(modifier = Modifier.fillMaxSize(), initialImageUrl = uiState.selectedImagePath, onImageFile = { file -> viewModel.onEvent(StoryEvent.ImageSelected(file)) })
            }

            Column {
                Text("Description", style = MaterialTheme.typography.titleMedium, color = MyWhite)
                Spacer(modifier = Modifier.height(8.dp))
                MyOutlinedTextField(value = uiState.caption, onValueChange = { viewModel.onEvent(StoryEvent.DescriptionChanged(it)) }, placeholder = "Description for your story...", singleLine = false, maxLines = 5)
            }

            uiState.submitError?.let { err -> Text(text = err, color = MyDarkGray) }

            if (uiState.isSubmitting) {
                LoadingDialog(message = "Creating…")
            }

            if (isEdit) {
                Text("Comments", style = MaterialTheme.typography.headlineSmall, color = MyWhite, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Start)
                Spacer(modifier = Modifier.height(8.dp))

                if (uiState.comments.isNotEmpty()) {
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(uiState.comments) { comment ->
                            val time = try {
                                val instant = Instant.parse(comment.createdAt)
                                val localTime = instant.atZone(ZoneId.of("Asia/Jakarta")).toLocalTime()
                                val formatter = DateTimeFormatter.ofPattern("HH:mm")
                                localTime.format(formatter)
                            } catch (_: Exception) {
                                comment.createdAt
                            }

                            MessageCard(Content = comment.comment, Time = time)
                        }
                    }
                }
            }
        }
    }
}
