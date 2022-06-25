package com.sample

import org.w3c.dom.url.URL

fun URL.toPageSearchParams(): PageSearchParams {
    val topic = searchParams.get("topic") ?: searchParams.get("t") ?: "all"
    val cctvName = searchParams.get("cctvName")
    val count = searchParams.get("count")?.let {
        try {
            it.toInt()
        } catch (e: NumberFormatException) {
            0
        }
    } ?: 10
    return PageSearchParams(topic, cctvName, count)
}