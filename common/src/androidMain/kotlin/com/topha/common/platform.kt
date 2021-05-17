package com.topha.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import com.skydoves.landscapist.glide.GlideImage

actual fun getPlatformName(): String {
    return "Android"
}

@Composable
actual fun RemoteImage(
    url: String,
    contentDescription: String?,
    modifier: Modifier,
    contentScale: ContentScale
) {
    GlideImage(
        imageModel = url,
        modifier = modifier,
        contentDescription = contentDescription,
        contentScale = ContentScale.Crop,
    )
}