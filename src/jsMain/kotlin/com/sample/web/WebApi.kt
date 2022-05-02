package com.sample.web

import com.sample.model.*
import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.decodeFromString

// https://data.gov.tw/

object WebApi {
    private val jsonClient = HttpClient {
        install(JsonFeature) { serializer = KotlinxSerializer() }
    }

    suspend fun getFromOutSide(): List<RoadData> {
        return jsonClient.get("https://tcgbusfs.blob.core.windows.net/dotapp/youbike/v2/youbike_immediate.json")
        //return jsonClient.get("https://odws.hccg.gov.tw/001/Upload/25/opendataback/9059/452/25d47dd1-ac2b-405f-ac52-ba2f8b3071b6.json")
    }

    suspend fun getUBikeList(): List<Ubike> {
        return jsonClient.get("https://tcgbusfs.blob.core.windows.net/dotapp/youbike/v2/youbike_immediate.json")
    }

    suspend fun getSellCount(): Map<String, RapidTestStore> {
        return jsonClient.get<String>("https://raw.githubusercontent.com/SiongSng/Rapid-Antigen-Test-Taiwan-Map/data/data/antigen.json")
            .fixEncodeJson()
    }

    private fun String.fixEncodeJson() = kotlinx.serialization.json.Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
        encodeDefaults = false
    }.decodeFromString<Map<String, RapidTestStore>>(this)

    // CMS https://itsapi.taipei.gov.tw/TPTS_API/roadInformation/CMSByLBS?distance=10000000&lng=121.460477&lat=25.019086&language=ZH
    suspend fun getCmsList(): Cms {
        return jsonClient.get<Cms>("https://itsapi.taipei.gov.tw/TPTS_API/roadInformation/CMSByLBS?distance=10000000&lng=121.460477&lat=25.019086&language=ZH")
    }

    suspend fun getStock(id: String = "2330"): Stock {
        return jsonClient.get<Stock>("https://ktor-success-version.herokuapp.com/stock")
    }

    suspend fun getHospital(): String {
        return jsonClient.get("https://ktor-success-version.herokuapp.com/hospital")
    }
}

// 台中空氣品質 https://datacenter.taichung.gov.tw/swagger/OpenData/1a129ee9-b4ce-491a-b7ed-a1f5e5ff19c1

// 水位 https://data.gov.tw/dataset/25768

// 台北交通即時資訊 https://data.gov.tw/dataset/128476

// 新竹及時停車  https://data.gov.tw/dataset/129136