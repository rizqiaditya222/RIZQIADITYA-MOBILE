package com.kotlin.rizqiaditya.presentation.story

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.kotlin.rizqiaditya.domain.model.Comment
import com.kotlin.rizqiaditya.presentation.components.common.ActionButton
import com.kotlin.rizqiaditya.presentation.components.common.MessageCard
import com.kotlin.rizqiaditya.presentation.components.common.TopBar
import com.kotlin.rizqiaditya.presentation.components.story.StorySlider
import com.kotlin.rizqiaditya.presentation.navigation.NavRoute
import com.kotlin.rizqiaditya.ui.theme.MyBlack
import com.kotlin.rizqiaditya.ui.theme.MyWhite
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.CachePolicy
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp as dpUnit
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import com.kotlin.rizqiaditya.di.NetworkModule

@Composable
fun StoryScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    parentNavController: NavHostController? = null,
    factory: ViewModelProvider.Factory
) {
    val appNav = parentNavController ?: navController

    val viewModel: StoryViewModel = viewModel(factory = factory)
    val uiState by viewModel.uiState.collectAsState()

    // load stories and comments when screen appears
    LaunchedEffect(Unit) {
        viewModel.loadActiveStory("") // load stories and comments for the first/active story
        viewModel.loadAllComment()
    }

    val storyList = uiState.stories
    val comments: List<Comment> = uiState.comments

    // Prefetch images when storyList changes to warm up cache and reduce UI load delay
    val context = LocalContext.current
    val imageWidthDp: Dp = 128.dp
    val imageHeightDp: Dp = 196.dp
    val imageSizePx = with(LocalDensity.current) { imageWidthDp.roundToPx() to imageHeightDp.roundToPx() }

    // Use a shared ImageLoader with the app OkHttp client and remember it
    val appOk = NetworkModule.provideOkHttpClient()
    val loader = remember(context) {
        ImageLoader.Builder(context)
            .okHttpClient(appOk)
            .build()
    }

    // remember which URLs we've already prefetched to avoid duplicate requests
    val prefetched = remember { mutableSetOf<String>() }

    LaunchedEffect(storyList.map { it.photoUrl }) {
        storyList.forEach { story ->
            try {
                val url = story.photoUrl
                if (url.isNotBlank() && !prefetched.contains(url)) {
                    val req = ImageRequest.Builder(context)
                        .data(url)
                        .diskCachePolicy(CachePolicy.ENABLED)
                        .memoryCachePolicy(CachePolicy.ENABLED)
                        .size(imageSizePx.first, imageSizePx.second)
                        .build()
                    loader.enqueue(req)
                    prefetched.add(url)
                }
            } catch (_: Exception) {
                // ignore individual prefetch failures
            }
        }
    }

    Column(modifier = modifier.background(color = MyBlack).padding(horizontal = 24.dp).fillMaxSize()) {
        TopBar(
            title = "Story",
            leftIcon = Icons.Default.Add,
            onLeftClick = {  },
            rightIcon = Icons.Default.Add,
            onRightClick = { appNav.navigate(NavRoute.STORYFORM) }
        )

        androidx.compose.material3.Text(
            "Current Stories",
            style = androidx.compose.material3.MaterialTheme.typography.headlineSmall,
            color = MyWhite,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start
        )

        Spacer(Modifier.height(8.dp))

        // pass story list and navigate to story form with isEdit=true when clicked
        StorySlider(storyList, onItemClick = { id -> appNav.navigate(NavRoute.storyFormRoute(isEdit = true, id = id)) })

        Spacer(Modifier.height(16.dp))

        androidx.compose.material3.Text(
            "Latest Comments",
            style = androidx.compose.material3.MaterialTheme.typography.headlineSmall,
            color = MyWhite,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start
        )

        Spacer(Modifier.height(8.dp))

        LazyColumn(
            modifier = Modifier
                .background(color = MyBlack)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(items = comments) { comment ->
                val time = try {
                    val instant = Instant.parse(comment.createdAt)
                    val localTime = instant.atZone(ZoneId.of("Asia/Jakarta")).toLocalTime()
                    val formatter = DateTimeFormatter.ofPattern("HH:mm")
                    localTime.format(formatter)
                } catch (_: Exception) {
                    comment.createdAt
                }

                MessageCard(
                    Content = comment.comment,
                    Time = time
                )
            }
        }
    }
}