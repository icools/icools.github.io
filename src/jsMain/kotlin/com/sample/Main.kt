package com.sample

import com.sample.components.Layout
import com.sample.components.MainContentLayout
import com.sample.content.*
import com.sample.page.MixedInfoPage
import com.sample.page.TaichungWaterPage
import com.sample.page.TainanCctvPage
import com.sample.page.TravelingPage
import com.sample.style.AppStylesheet
import com.sample.viewmodel.MainViewModel
import kotlinx.browser.sessionStorage
import kotlinx.browser.window
import org.jetbrains.compose.web.css.Style
import org.jetbrains.compose.web.renderComposable
import org.w3c.dom.get
import org.w3c.dom.set
import org.w3c.dom.url.URL
fun main() {
    lateinit var viewModel: MainViewModel
    //kotlinx.js.jso<String>().
    //kotlinx.browser.window.applicationCache.

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
                        MixedInfoPage("即時資訊卡片", "Realtime dashboard", viewModel.uiState)
                    }
                    TopicEnum.WATER -> {
                        TaichungWaterPage("台中水情", "about the water", viewModel.taichungAirList)
                    }
                    TopicEnum.TRAVELING -> {
                        TravelingPage("觀光旅遊", "about 觀光", viewModel.travelingResponse)
                    }
                    TopicEnum.CCTV -> {
                        TainanCctvPage(
                            "台南CCTV",
                            "cctv",
                            params.cctvName,
                            viewModel.tainanCctvList
                        )
                    }
                    else -> {
                        MixedInfoPage("即時資訊卡片", "Realtime dashboard", viewModel.uiState)
                        TaichungWaterPage("台中空氣", "about the water", viewModel.taichungAirList)
                    }
                }
            }
            PageFooter()
        }
    }
}

enum class TopicEnum(val value: String) {
    CCTV("cctv"),
    TRAVELING("traveling"),
    ALL("all"),
    UBIKE("ubike"),
    WATER("water"),
    //STOCK("stock"),
    //AIR("AIR"),
    REALTIME("realtime");

    companion object {
        fun parsing(topic: String) = values().firstOrNull {
            it.value == topic
        } ?: ALL
    }
}

private fun URL.toPageSearchParams(): PageSearchParams {
    val topic = searchParams.get("topic") ?: "all"
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

private data class PageSearchParams(val topic: String, val cctvName: String? = null, val count: Int = 10)