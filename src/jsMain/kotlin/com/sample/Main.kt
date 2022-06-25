package com.sample

import androidx.compose.runtime.Composable
import com.sample.components.CardDark
import com.sample.components.Layout
import com.sample.components.LinkOnCard
import com.sample.components.MainContentLayout
import com.sample.content.CardStylePage
import com.sample.content.Header
import com.sample.content.PageFooter
import com.sample.page.*
import com.sample.style.AppStylesheet
import com.sample.style.WtCols
import com.sample.viewmodel.MainViewModel
import kotlinx.browser.window
import org.jetbrains.compose.web.css.Style
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.renderComposable
import org.w3c.dom.url.URL

// TODO https://openapi.twse.com.tw/#/%E6%8C%87%E6%95%B8/get_indicesReport_TAI50I 股票
// TODO 將https://github.com/icools/AllAndroidProject/tree/master/AllAndroidProject 移除

fun main() {
    lateinit var viewModel: MainViewModel
    renderComposable(rootElementId = "root") {
        Style(AppStylesheet)
        val params = URL(window.location.href).toPageSearchParams()
        val topic = TopicEnum.parsing(params.topic)
        viewModel = MainViewModel(topic)
        Layout {
            Header()
            MainContentLayout {
                //IntroCustom(viewModel.uiState)
                //Intro()
                //ComposeWebLibraries()
                //CodeSamples()
                //JoinUs()
                when (topic) {
                    TopicEnum.REALTIME -> {
                        MixedInfoPage("即時資訊卡片", "Realtime dashboard", viewModel.cmsResponse)
                    }
                    TopicEnum.WATER -> {
                        TaichungWaterPage("台中水情", "about the water", viewModel.taichungAirList)
                    }
                    TopicEnum.TRAVELING -> {
                        TravelingPage("觀光旅遊", "about 觀光", viewModel.travelingResponse)
                    }
                    TopicEnum.CCTV -> AddTainanCctv(viewModel, params.cctvName)
                    TopicEnum.STOCK -> AddStockPage(viewModel)
                    else -> {
                        //MixedInfoPage("即時資訊卡片", "Realtime dashboard", viewModel.uiState)
                        //TaichungWaterPage("台中空氣", "about the water", viewModel.taichungAirList)
                        AllServiceCard()
                    }
                }
            }
            PageFooter()
        }
    }
}

@Composable
fun AllServiceCard(){
    CardStylePage("Ktor Compose for web", "所有的api 服務清單") {
        ServiceCard("台南CCTV", "from tainan Open source api取得", TopicEnum.CCTV)
        ServiceCard("Twse股票", "台灣股票OpenApi", TopicEnum.STOCK)
        ServiceCard("觀光局", "熱門景點", TopicEnum.TRAVELING)
        ServiceCard("路況資訊", "台北CMS即時資訊", TopicEnum.REALTIME)
        ServiceCard("快篩即時資訊", "施工中", TopicEnum.ALL)
        ServiceCard("台中空氣資訊", "施工中", TopicEnum.WATER)
        ServiceCard("UBike", "施工中", TopicEnum.UBIKE)
    }
}

@Composable
fun ServiceCard(title: String,description: String = "null" , topic: TopicEnum){
    val url = "${window.location}?t=${topic.value}"
    CardDark(
        title = title,
        links = listOf(LinkOnCard("前往",url)),
        wtExtraStyleClasses = listOf(WtCols.wtCol4, WtCols.wtColMd6, WtCols.wtColSm12)
    ){
        Text(description)
    }
}

@Composable
fun AddTainanCctv(viewModel: MainViewModel, cctvName: String?) {
    TainanCctvPage(
        "台南CCTV",
        "cctv",
        cctvName,
        viewModel.tainanCctvList
    )
}

@Composable
fun AddStockPage(viewModel: MainViewModel) {
    StockPage(
        "TWSET股票資訊",
        "最近上市股票",
        viewModel.stockNewToList
    )
}

enum class TopicEnum(val value: String) {
    CCTV("cctv"),
    TRAVELING("traveling"),
    ALL("all"),
    UBIKE("ubike"),
    WATER("water"),
    STOCK("stock"),
    REALTIME("realtime");
    //AIR("AIR"),

    companion object {
        fun parsing(topic: String) = values().firstOrNull {
            it.value == topic
        } ?: ALL
    }
}

data class PageSearchParams(val topic: String, val cctvName: String? = null, val count: Int = 10)