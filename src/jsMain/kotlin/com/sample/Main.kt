package com.sample

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import com.sample.components.*
import com.sample.content.CardStylePage
import com.sample.content.Header
import com.sample.content.PageFooter
import com.sample.model.Cms
import com.sample.model.Ubike
import com.sample.model.stock.StockNewTo
import com.sample.page.*
import com.sample.style.AppStylesheet
import com.sample.viewmodel.MainViewModel
import com.sample.viewmodel.UbikeViewModel
import kotlinx.browser.localStorage
import kotlinx.browser.window
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import org.jetbrains.compose.web.css.Style
import org.jetbrains.compose.web.renderComposable
import org.w3c.dom.get
import org.w3c.dom.url.URL

// TODO https://openapi.twse.com.tw/#/%E6%8C%87%E6%95%B8/get_indicesReport_TAI50I 股票
// TODO 將https://github.com/icools/AllAndroidProject/tree/master/AllAndroidProject 移除
// TODO cctv viewer page
// TODO add timer count
// TODO add filter
// TODO add location request
// TODO add Map https://www.google.com/maps/search/25.2,121.2

//MixedInfoPage("即時資訊卡片", "Realtime dashboard", viewModel.uiState)
//TaichungWaterPage("台中空氣", "about the water", viewModel.taichungAirList)
//IntroCustom(viewModel.uiState)
//Intro()
//ComposeWebLibraries()
//CodeSamples()
//JoinUs()

fun main() {
    renderComposable(rootElementId = "root") {
        Style(AppStylesheet)
        val (p, name) = URL(window.location.href).toPageSearchParams()
        val topic = TopicEnum.parsing(p)

        val scope = rememberCoroutineScope()
        val viewModel = MainViewModel(topic,scope)
        val ubikeViewModel = UbikeViewModel(topic,scope)

        Layout {
            Header()
            MainContentLayout {
                when (topic) {
                    TopicEnum.CMS -> MixedInfoPage(
                        title = "即時資訊卡片",
                        description = "Realtime dashboard",
                        cms = viewModel.cmsResponse
                    )
                    TopicEnum.WATER -> TaichungAirPage(
                        title = "台中空氣",
                        description = "about the water",
                        airList = viewModel.taichungAirList
                    )
                    TopicEnum.TRAVELING -> TravelingPage(
                        title = "觀光旅遊",
                        description = "about 觀光",
                        travelResponse = viewModel.travelingResponse
                    )
                    TopicEnum.CCTV -> TainanCctvPage(
                        title = "台南CCTV",
                        description = "cctv",
                        cctvFilterName = name,
                        cctvList = viewModel.tainanCctvList
                    )
                    TopicEnum.STOCK -> StockPage(
                        title = "TWSET股票資訊",
                        description = "最近上市股票",
                        stockList = viewModel.stockNewToList
                    )
                    TopicEnum.UBIKE -> UBikePage(
                        title = "UBike",
                        description = "台北剩餘車位",
                        ubikeList = ubikeViewModel.ubikeList
                    )
                    else -> {
                        AllServiceCard()
                    }
                }
            }
            PageFooter()
        }
    }
}

@Composable
fun AllServiceCard() {
    val all = listOf(
        Triple("台南CCTV", "from tainan Open source api取得", TopicEnum.CCTV),
        Triple("Twse股票", "台灣股票OpenApi", TopicEnum.STOCK),
    )

    CardStylePage("Ktor Compose for web", "所有的api 服務清單") {
        val stockCache = getLocalStorageListByTopic<StockNewTo>(TopicEnum.STOCK)
            ?.random()?.company ?: "台灣股票OpenApi"

        val cmsCache = getLocalStorageByTopic<Cms>(TopicEnum.CMS)?.locations
            ?.random()?.cmsMsg ?: "台北CMS即時資訊"

        val ubikeDesc = getLocalStorageListByTopic<Ubike>(TopicEnum.UBIKE)?.random()?.let{
            "${it.stationName} + ${it.sbi}/${it.totalCount}"
        } ?: "剩餘車位"

        ServiceCard(
            title = "台南CCTV",
            description = "from tainan Open source api取得",
            topic = TopicEnum.CCTV
        )

        ServiceCard(
            title = "Twse股票",
            description = stockCache,
            topic = TopicEnum.STOCK
        )

        ServiceCard(
            title = "觀光局",
            description = "熱門景點",
            topic = TopicEnum.TRAVELING
        )

        ServiceCard(
            title = "路況資訊",
            description = cmsCache,
            topic = TopicEnum.CMS
        )

        ServiceCard(
            title = "台中空氣資訊",
            description = "施工中",
            topic = TopicEnum.WATER
        )

        ServiceCard(
            title = "UBike",
            description = ubikeDesc,
            topic = TopicEnum.UBIKE
        )
    }
}

@OptIn(InternalSerializationApi::class)
private inline fun <reified T: Any> getLocalStorageByTopic(topic: TopicEnum): T? {
    return localStorage.get(topic.value)?.let {
        Json.decodeFromString(T::class.serializer(), it)
    }
}

@OptIn(InternalSerializationApi::class)
private inline fun <reified T: Any> getLocalStorageListByTopic(topic: TopicEnum): List<T>? {
    return localStorage.get(topic.value)?.let {
        Json.decodeFromString(ListSerializer(T::class.serializer()), it)
    }
}