package com.sample

import kotlinx.browser.localStorage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.PolymorphicSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.nullable
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.serializer
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

val json = Json {
    useArrayPolymorphism = true
    serializersModule = SerializersModule {
        polymorphic(Any::class) {
            subclass(List::class, ListSerializer(PolymorphicSerializer(Any::class).nullable))
        }
    }
}

@OptIn(InternalSerializationApi::class)
suspend inline fun <reified T: Any> storageCacheFlow(
    topic: TopicEnum,
    crossinline fetchFromService: suspend () -> T
) = flow {

    val serializer = T::class.serializer()
    val topicType = topic.value
    localStorage.get(topicType)?.let {
        val result = Json.decodeFromString(serializer,it)
        emit(result)
    }

    fetchFromService().let{ result ->
        val jsonString = json.encodeToString(serializer,result)
        localStorage.set(topicType,jsonString)
        emit(result)
    }
}

@OptIn(InternalSerializationApi::class)
inline fun <reified T: Any> storageCacheList(
    topic: TopicEnum,
    crossinline fetchFromService: suspend () -> List<T>
) = flow {
    val serializer = ListSerializer(T::class.serializer())
    val topicType = topic.value
    localStorage.get(topicType)?.let {
        val result = Json.decodeFromString(ListSerializer(T::class.serializer()),it)
        emit(result)
    }

    fetchFromService().let{ result ->
        emit(result)
        val jsonString = json.encodeToString(serializer,result)
        localStorage.set(topicType,jsonString)
    }
}.flowOn(Dispatchers.Default)