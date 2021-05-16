package com.topha.common
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import com.topha.common.backends.core.PostData
import com.topha.common.backends.core.SubData
import com.topha.common.backends.core.win.WinProvider
import java.io.ByteArrayOutputStream
import java.net.HttpURLConnection
import java.net.URL
import javax.imageio.ImageIO
import androidx.compose.material.icons.materialIcon;
import androidx.compose.ui.layout.ContentScale

@Composable
fun SubsList(subs: List<SubData>) {
    val uriHandler = LocalUriHandler.current
    for (sub in subs) {
        val annotatedString = buildAnnotatedString {
            append(sub.name)
            addStringAnnotation(
                tag = "URL",
                annotation = sub.url,
                start = 0,
                end = sub.name.length
            )
        }
        Row {
            ClickableText(
                text = annotatedString,

                style = MaterialTheme.typography.caption,
                onClick = {
                    println("click")
                    annotatedString
                        .getStringAnnotations("URL", it, it)
                        .firstOrNull()?.let { stringAnnotation ->
                            uriHandler.openUri(stringAnnotation.item)
                        }
                }
            )
        }
    }
}

@Composable
fun PostCard(post: PostData) {
    Card(modifier = Modifier.fillMaxWidth().padding(8.dp), elevation = 8.dp) {
        Column (Modifier.fillMaxWidth()) {
            Text(
                text = post.name,
                style = MaterialTheme.typography.h5
            )
            Text(
                modifier = Modifier.align(Alignment.End),
                text = "By " + post.author,
                style = MaterialTheme.typography.caption
            )
        }

        if (post.thumbnail != null && post.thumbnail.isNotEmpty())
            RemoteImage(url = post.thumbnail,
            contentDescription = "thumbnail",
                modifier = Modifier, contentScale = ContentScale.None)
    }
}
@Composable
fun PostsList(posts: List<PostData>) {
    Column {
        for (post in posts) {
            PostCard(post)
        }
    }
}

@Composable
fun App() {
    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Open))
    val provider = WinProvider()
    val subs = provider.getSubs().toMutableList()
    val posts = provider.getPosts().toMutableList()
    val scrollState = rememberScrollState()

    fun reloadPosts() {
        posts.clear()
        for (post in provider.getPosts()) {
            posts.add(post)
        }
    }
    MaterialTheme {
        Scaffold(
            scaffoldState = scaffoldState,
            drawerContent = {
                SubsList(subs)
            },
            floatingActionButton = {
                FloatingActionButton(onClick = {reloadPosts()}) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Refresh"
                    )
                }

            }, content = {

                Column(
                    modifier = Modifier.verticalScroll(scrollState)
                ) {
                    PostsList(posts)
                }
            }
        )
    }

}
