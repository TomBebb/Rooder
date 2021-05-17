package com.topha.common.backends.core

import org.jsoup.nodes.Element

data class PostContent(private val postDom: Element) {
    val html: String
        get() = postDom.html()

}