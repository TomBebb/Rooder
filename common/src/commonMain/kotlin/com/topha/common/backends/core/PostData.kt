package com.topha.common.backends.core

import java.util.*

class PostData(val name: String, val url: String, val author: String, val score: Int, val thumbnail: String?, val content: PostContent, val publishedDateTime: Date) {
}