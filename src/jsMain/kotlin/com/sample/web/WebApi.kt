package com.sample.web

import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*

val jsonClient = HttpClient {
    install(JsonFeature) { serializer = KotlinxSerializer() }
}

suspend fun getFromOutSide(): String {
    return jsonClient.get("https://data.tainan.gov.tw/dataset/a37f3986-9f6e-4a6a-a628-a83ca9b5698b/resource/6d5de44b-4d70-4e76-893a-73f79891800e/download/opendata.json")
    //return jsonClient.get("https://odws.hccg.gov.tw/001/Upload/25/opendataback/9059/452/25d47dd1-ac2b-405f-ac52-ba2f8b3071b6.json")
}