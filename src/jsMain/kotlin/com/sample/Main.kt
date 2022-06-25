package com.sample

import androidx.compose.runtime.Composable
import com.sample.components.*
import com.sample.content.CardStylePage
import com.sample.content.Header
import com.sample.content.PageFooter
import com.sample.model.Cms
import com.sample.model.Ubike
import com.sample.model.stock.StockNewTo
import com.sample.page.*
import com.sample.style.AppStylesheet
import com.sample.style.WtCols
import com.sample.viewmodel.MainViewModel
import kotlinx.browser.localStorage
import kotlinx.browser.window
import org.jetbrains.compose.web.css.Style
import org.jetbrains.compose.web.dom.Text
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
    lateinit var viewModel: MainViewModel
    renderComposable(rootElementId = "root") {
        Style(AppStylesheet)
        val (p, name) = URL(window.location.href).toPageSearchParams()
        val topic = TopicEnum.parsing(p)

        viewModel = MainViewModel(topic)
        Layout {
            Header()
            MainContentLayout {
                when (topic) {
                    TopicEnum.CMS -> MixedInfoPage("即時資訊卡片", "Realtime dashboard", viewModel.cmsResponse)
                    TopicEnum.WATER -> TaichungAirPage("台中空氣", "about the water", viewModel.taichungAirList)
                    TopicEnum.TRAVELING -> AddTravelingPage(viewModel)
                    TopicEnum.CCTV -> AddTainanCctv(viewModel, name)
                    TopicEnum.STOCK -> AddStockPage(viewModel)
                    TopicEnum.UBIKE -> AddUBikePage(viewModel)
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
        ServiceCard("台南CCTV", "from tainan Open source api取得", TopicEnum.CCTV)

        val stockCache = localStorage.get(TopicEnum.STOCK.value)?.let{
            StockNewTo.fromJson(it)
        }?.random()?.let{
            it.company
        } ?: "台灣股票OpenApi"
        ServiceCard("Twse股票", stockCache, TopicEnum.STOCK)

        ServiceCard("觀光局", "熱門景點", TopicEnum.TRAVELING)

        val cmsCache = localStorage.get(TopicEnum.CMS.value)?.let{
            Cms.fromJson(it).locations
        }?.random()?.let{
            it.cmsMsg
        } ?: "台北CMS即時資訊"
        ServiceCard("路況資訊", cmsCache, TopicEnum.CMS)

        ServiceCard("台中空氣資訊", "施工中", TopicEnum.WATER)

        (localStorage.get(TopicEnum.UBIKE.value)?.let{
            Ubike.fromJson(it)
        }?.random()?.let{
            "${it.stationName} + ${it.sbi}/${it.totalCount}"
        } ?: "剩餘車位").let{
            ServiceCard("UBike", it, TopicEnum.UBIKE)
        }

        //ServiceCard("快篩即時資訊", "施工中", TopicEnum.ALL)
    }
}