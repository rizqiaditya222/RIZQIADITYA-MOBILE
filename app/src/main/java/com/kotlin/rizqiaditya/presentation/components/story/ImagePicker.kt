package com.kotlin.rizqiaditya.presentation.components.story

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.kotlin.rizqiaditya.presentation.util.uriToFile
import java.io.File
import androidx.compose.ui.layout.ContentScale
import androidx.compose.material3.Surface
import androidx.compose.ui.window.Dialog
import com.kotlin.rizqiaditya.ui.theme.MyWhite
import coil.ImageLoader
import coil.request.CachePolicy
import com.kotlin.rizqiaditya.di.NetworkModule

@Composable
fun ImagePicker(
    modifier: Modifier = Modifier,
    initialImageUrl: String? = null,
    onImageFile: (File?) -> Unit
) {
    val context = LocalContext.current
    val selectedUri = remember { mutableStateOf<Uri?>(null) }
    val showPreview = remember { mutableStateOf(false) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedUri.value = uri
        val file = uri?.let { uriToFile(it, context) }
        onImageFile(file)
    }

    // Use app OkHttp client for network consistency (same as StorySlider) and enable logging
    val appOk = NetworkModule.provideOkHttpClient()
    val loader = ImageLoader.Builder(context)
        .okHttpClient(appOk)
        .build()

    Box(
        modifier = modifier.clickable { launcher.launch("image/*") },
        contentAlignment = Alignment.Center
    ) {
        // If user selected a local URI, show it. Otherwise, if an initialImageUrl is provided, show remote preview.
        selectedUri.value?.let { uri ->
            val req = ImageRequest.Builder(context)
                .data(uri)
                .diskCachePolicy(CachePolicy.ENABLED)
                .memoryCachePolicy(CachePolicy.ENABLED)
                .listener(
                    onError = { request, result ->
                        Log.w("ImagePicker", "Local image load error: ${request.data} -> ${result.throwable?.message}")
                    }
                )
                .build()

            AsyncImage(
                model = req,
                imageLoader = loader,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            IconButton(
                onClick = { showPreview.value = true },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
            ) {
                Icon(imageVector = Icons.Default.Visibility, contentDescription = "Preview", tint = MyWhite)
            }

            if (showPreview.value) {
                Dialog(onDismissRequest = { showPreview.value = false }) {
                    Surface(modifier = Modifier.size(320.dp)) {
                        AsyncImage(
                            model = ImageRequest.Builder(context).data(uri).build(),
                            imageLoader = loader,
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }
        } ?: run {
            // no local selection, show initialImageUrl if present
            if (!initialImageUrl.isNullOrBlank()) {
                val req = ImageRequest.Builder(context)
                    .data(initialImageUrl)
                    .diskCachePolicy(CachePolicy.ENABLED)
                    .memoryCachePolicy(CachePolicy.ENABLED)
                    .listener(onError = { request, result ->
                        Log.w("ImagePicker", "Remote image load error: ${request.data} -> ${result.throwable?.message}")
                    }, onSuccess = { request, _ ->
                        Log.d("ImagePicker", "Remote image loaded: ${request.data}")
                    })
                    .build()

                AsyncImage(
                    model = req,
                    imageLoader = loader,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                IconButton(
                    onClick = { showPreview.value = true },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                ) {
                    Icon(imageVector = Icons.Default.Visibility, contentDescription = "Preview", tint = MyWhite)
                }

                if (showPreview.value) {
                    Dialog(onDismissRequest = { showPreview.value = false }) {
                        Surface(modifier = Modifier.size(320.dp)) {
                            AsyncImage(
                                model = ImageRequest.Builder(context).data(initialImageUrl).build(),
                                imageLoader = loader,
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                }

            } else {
                IconButton(onClick = { launcher.launch("image/*") }) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Pick image",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}
