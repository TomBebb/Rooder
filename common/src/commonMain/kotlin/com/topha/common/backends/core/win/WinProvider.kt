package com.topha.common.backends.core.win

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.text.SimpleDateFormat
import java.util.*
import com.topha.common.backends.core.*;

class WinProvider: IProvider {
    private val urls = arrayOf(
        "https://kotakuinaction2.win/"
    );
    private val url
        get() = urls[0];
    private val dom: Document = Jsoup.connect(url).get();
    private fun getDefaultSubs(): Iterable<SubData> {

        val defaults = dom.getElementsByClass("default-communities")[0]

        return defaults.getElementsByTag("a")
            .map { SubData(it.text(), it.attr("href")) }
    }

    fun getPosts(): Iterable<PostData> {
        val postList = dom.getElementsByClass("post-list")[0];
        return postList.getElementsByClass("post")
            .map {
                val body = it.getElementsByClass("body")[0];
                val titleEl = body.getElementsByClass("title")[0];
                val postUrl = url + titleEl.attr("href");
                val author: String = it.dataset()["author"]!!;
                val title = titleEl.text();
                val score: Int = it.getElementsByClass("count")[0].text().toInt();
                val content = it.getElementsByClass("inner")[0];

                val postContent = PostContent(content);

                val thumbnailUrl: String? = it.getElementsByClass("thumb")
                    .first()
                    .getElementsByTag("img")
                    .attr("src");

                val fmt = SimpleDateFormat(
                    "yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US
                )
                val publishedTime = fmt.parse(it.getElementsByClass("timeago")
                    .attr("datetime")
                );
                PostData(title, postUrl, author, score, thumbnailUrl, postContent, publishedTime)
            }
    }
    override fun getSubs(): Iterable<SubData> {
        return getDefaultSubs();
    }

}