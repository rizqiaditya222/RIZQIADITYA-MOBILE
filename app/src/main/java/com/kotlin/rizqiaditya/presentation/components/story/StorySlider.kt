package com.kotlin.rizqiaditya.presentation.components.story

import android.util.Log
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.disk.DiskCache
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.kotlin.rizqiaditya.di.NetworkModule
import com.kotlin.rizqiaditya.domain.model.Story
import com.kotlin.rizqiaditya.ui.theme.MyBaseGray
import com.kotlin.rizqiaditya.ui.theme.MyDarkGray
import java.io.File

@Composable
fun StorySlider(
    stories: List<Story>,
    isLoading: Boolean = false,
    onItemClick: (String) -> Unit = {}
) {
    val context = LocalContext.current
    val imageWidthDp: Dp = 128.dp
    val imageHeightDp: Dp = 196.dp
    val imageSizePx = with(LocalDensity.current) { imageWidthDp.roundToPx() to imageHeightDp.roundToPx() }

    val appOk = NetworkModule.provideOkHttpClient()
    val imageCacheDir = java.io.File(context.cacheDir, "coil_image_cache")
    val loader = remember(context) {
        ImageLoader.Builder(context)
            .okHttpClient(appOk)
            .diskCache {
                DiskCache.Builder()
                    .directory(imageCacheDir)
                    .maxSizeBytes(50L * 1024L * 1024L)
                    .build()
            }
            .build()
    }

    LazyRow(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        when {
            isLoading -> {
                // Show 3 shimmer placeholders while loading
                items(3) {
                    ShimmerStoryItem(
                        width = imageWidthDp,
                        height = imageHeightDp
                    )
                }
            }
            stories.isEmpty() -> {
                // Show 3 empty placeholders if no stories
                items(3) {
                    EmptyStoryItem(
                        width = imageWidthDp,
                        height = imageHeightDp
                    )
                }
            }
            else -> {
                // Show actual stories
                items(stories) { story ->
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(color = MyDarkGray)
                            .size(width = imageWidthDp, height = imageHeightDp)
                            .clickable { onItemClick(story.id) }
                    ) {
                        val request = ImageRequest.Builder(context)
                            .data(story.photoUrl)
                            .size(imageSizePx.first, imageSizePx.second)
                            .diskCachePolicy(CachePolicy.ENABLED)
                            .memoryCachePolicy(CachePolicy.ENABLED)
                            .listener(
                                onError = { request, result ->
                                    val causeMsg = result.throwable.message ?: "unknown"
                                    Log.w("StorySlider", "Image load error for: ${request.data} -> $causeMsg")
                                },
                                onSuccess = { request, _ ->
                                    Log.d("StorySlider", "Image loaded: ${request.data}")
                                }
                            )
                            .crossfade(true)
                            .build()

                        AsyncImage(
                            model = request,
                            imageLoader = loader,
                            contentDescription = "Story Image",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ShimmerStoryItem(
    width: Dp,
    height: Dp
) {
    val transition = rememberInfiniteTransition(label = "shimmer")
    val translateAnim by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmer"
    )

    val shimmerColors = listOf(
        Color(0xFFB0B0B0),
        Color(0xFFC0C0C0),
        Color(0xFFD0D0D0),
        Color(0xFFC0C0C0),
        Color(0xFFB0B0B0)
    )

    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset(translateAnim - 1000f, translateAnim - 1000f),
        end = Offset(translateAnim, translateAnim)
    )

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .size(width = width, height = height)
            .background(brush = brush)
    )
}

@Composable
private fun EmptyStoryItem(
    width: Dp,
    height: Dp
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .size(width = width, height = height)
            .background(color = MyBaseGray)
    )
}