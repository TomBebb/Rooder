package com.topha.common.backends.core

import org.jsoup.nodes.Element

class PostContent(private val postDom: Element) {
    val html: String
        get() = postDom.html()

}