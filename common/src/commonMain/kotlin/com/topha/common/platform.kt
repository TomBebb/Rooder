package com.topha.common

import androidx.compose.runtime.Composable;
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale

expect fun getPlatformName(): String

@Composable
expect fun RemoteImage(
    url: String,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Fit
)