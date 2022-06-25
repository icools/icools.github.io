package com.sample

import kotlinx.browser.localStorage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.w3c.dom.get
import org.w3c.dom.set
import org.w3c.dom.url.URL

fun URL.toPageSearchParams(): PageSearchParams {
    val topic = searchParams.get("topic") ?: searchParams.get("t") ?: "all"
    val cctvName = searchParams.get("name") ?: searchParams.get("n")
    val count = searchParams.get("count")?.let {
        try {
            it.toInt()
        } catch (e: NumberFormatException) {
            0
        }
    } ?: 20
    return PageSearchParams(topic, cctvName, count)
}

fun <T> CoroutineScope.storageCache(
    topic: TopicEnum,
    loadFromJson: (String) -> T,
    objectToString: (T) -> String,
    fetchFromService: suspend () -> T,
    onUpdate: (T) -> Unit
) {
    val topicType = topic.value
    localStorage.get(topicType)?.let {
        onUpdate.invoke(loadFromJson(it))
    }

    launch {
        fetchFromService().let{ result ->
            onUpdate.invoke(result)
            objectToString(result).let{
                localStorage.set(topicType,it)
            }
        }
    }
}