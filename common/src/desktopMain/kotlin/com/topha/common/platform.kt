package com.topha.common

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import java.io.ByteArrayOutputStream
import java.net.HttpURLConnection
import java.net.URL
import javax.imageio.ImageIO

actual fun getPlatformName(): String {
    return "Desktop"
}


fun loadNetworkImage(link: String): ImageBitmap {
    val url = URL(link)

    val connection = url.openConnection() as HttpURLConnection
    connection.connect()

    val inputStream = connection.inputStream
    val bufferedImage = ImageIO.read(inputStream)

    val stream = ByteArrayOutputStream()
    ImageIO.write(bufferedImage, "png", stream)
    val byteArray = stream.toByteArray()

    return org.jetbrains.skija.Image.makeFromEncoded(byteArray).asImageBitmap()
}
@Composable
actual fun RemoteImage(
    url: String,
    contentDescription: String?,
    modifier: Modifier,
    contentScale: ContentScale
) {
    val image = remember(url) { mutableStateOf<ImageBitmap?>(loadNetworkImage(url)) }
    if (image.value != null) {
        Image(
            bitmap = image.value!!,
            contentDescription = contentDescription,
            modifier = modifier,
            contentScale = contentScale
        )
    }
}