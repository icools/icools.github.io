package com.sample.web

import com.sample.model.RapidTestStore
import com.sample.model.RoadData
import com.sample.model.Ubike
import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import kotlinx.serialization.decodeFromString

val jsonClient = HttpClient {
    install(JsonFeature) { serializer = KotlinxSerializer() }
}

suspend fun getFromOutSide(): List<RoadData> {
    return jsonClient.get("https://tcgbusfs.blob.core.windows.net/dotapp/youbike/v2/youbike_immediate.json")
    //return jsonClient.get("https://odws.hccg.gov.tw/001/Upload/25/opendataback/9059/452/25d47dd1-ac2b-405f-ac52-ba2f8b3071b6.json")
}

suspend fun getUBikeList(): List<Ubike>{
    return jsonClient.get("https://tcgbusfs.blob.core.windows.net/dotapp/youbike/v2/youbike_immediate.json")
}

suspend fun getSellCount(): Map<String, RapidTestStore>{
    return jsonClient.get<String>("https://raw.githubusercontent.com/SiongSng/Rapid-Antigen-Test-Taiwan-Map/data/data/antigen.json").fixEncodeJson()
}

private fun String.fixEncodeJson() = kotlinx.serialization.json.Json {
    ignoreUnknownKeys = true
    coerceInputValues = true
    encodeDefaults = false
}.decodeFromString<Map<String, RapidTestStore>>(this)

// 股價 https://zys-notes.blogspot.com/2020/01/api.html