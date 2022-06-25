package com.sample.web

import com.sample.model.*
import com.sample.model.stock.StockNewTo
import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.decodeFromString

// https://data.gov.tw/

object WebApi {
    private const val USING_LOCALHOST = false

    private val jsonClient = HttpClient {
        install(JsonFeature) { serializer = KotlinxSerializer() }
    }

    suspend fun getFromOutSide(): List<RoadData> {
        return jsonClient.get("https://tcgbusfs.blob.core.windows.net/dotapp/youbike/v2/youbike_immediate.json")
        //return jsonClient.get("https://odws.hccg.gov.tw/001/Upload/25/opendataback/9059/452/25d47dd1-ac2b-405f-ac52-ba2f8b3071b6.json")
    }

    suspend fun getUBikeList(): List<Ubike> = jsonClient.get("https://tcgbusfs.blob.core.windows.net/dotapp/youbike/v2/youbike_immediate.json")

    suspend fun getSellCount(): Map<String, RapidTestStore> {
        return jsonClient.get<String>("https://raw.githubusercontent.com/SiongSng/Rapid-Antigen-Test-Taiwan-Map/data/data/antigen.json")
            .fixEncodeJson()
    }

    private fun String.fixEncodeJson() = kotlinx.serialization.json.Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
        encodeDefaults = false
    }.decodeFromString<Map<String, RapidTestStore>>(this)

    suspend fun getCmsList(): Cms {
        return jsonClient.get("https://itsapi.taipei.gov.tw/TPTS_API/roadInformation/CMSByLBS?distance=10000000&lng=121.460477&lat=25.019086&language=ZH")
    }

    suspend fun getTaipeiTraffic(): TaipeiTraffic{
        return jsonClient.get("https://tcgbusfs.blob.core.windows.net/dotapp/news.json")
    }

    suspend fun getStock(id: String = "2330"): Stock {
        return jsonClient.get(getUrl("/stock"))
    }
    suspend fun getHospital(): String = jsonClient.get(getUrl("/hospital"))

    suspend fun getCross(): String {
        return jsonClient.get(getUrl("/cross"))
    }

    suspend fun getTaichungAir(): List<TaichungAir>{
        return jsonClient.get("https://datacenter.taichung.gov.tw/swagger/OpenData/1a129ee9-b4ce-491a-b7ed-a1f5e5ff19c1")
    }

    private fun getUrl(actionName: String) = if (USING_LOCALHOST) {
        "http://localhost:8088"
    } else {
        "https://ktor-success-version.herokuapp.com"
    }.let {
        "$it$actionName"
    }

    object Tainan{
        suspend fun getCctv(): List<TainanCctv> = jsonClient.get(getUrl("/tainanCctv"))
    }

    object Traveling{

        suspend fun getHotTravelSpot(): TravelResponse = jsonClient.get(getUrl("/traveling"))
    }

    object TwseStock{

        /**
         * 最近上市股票
         */
        suspend fun getNewTo(): List<StockNewTo> = jsonClient.get(getUrl("/twse_stock"))

        /**
         * 董監持股
         */
        // https://openapi.twse.com.tw/#/%E5%85%AC%E5%8F%B8%E6%B2%BB%E7%90%86/get_opendata_t187ap11_L

        /**
         * 每日收盤行情-大盤統計資訊
         */

        /**
         * 電子式交易統計資訊
         */
    }
}

// 水位 https://data.gov.tw/dataset/25768

// 新竹及時停車  https://data.gov.tw/dataset/129136

// CMS https://itsapi.taipei.gov.tw/TPTS_API/roadInformation/CMSByLBS?distance=10000000&lng=121.460477&lat=25.019086&language=ZH

// 疫情 https://od.cdc.gov.tw/eic/covid19/covid19_tw_stats.csv