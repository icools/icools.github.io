package com.sample

import com.sample.components.Layout
import com.sample.components.MainContentLayout
import com.sample.content.*
import com.sample.style.AppStylesheet
import com.sample.viewmodel.MainViewModel
import kotlinx.browser.window
import org.jetbrains.compose.web.css.Style
import org.jetbrains.compose.web.renderComposable
import org.w3c.dom.url.URL

fun main() {
    val viewModel = MainViewModel()
    renderComposable(rootElementId = "root") {
        Style(AppStylesheet)

        val params = URL(window.location.href).toPageSearchParams()
        Layout {
            Header()
            MainContentLayout {
                //IntroCustom(viewModel.uiState)
                //Intro()
                //ComposeWebLibraries()
                //CodeSamples()
                //JoinUs()
                when(params.topic){
                    "realtime" ->{
                        MixedInfoPage("即時資訊卡片", "Realtime dashboard", viewModel.uiState)
                    }
                    "water" ->{
                        TaichungWaterPage("台中水情", "about the water", viewModel.taichungAirList)
                    }
                    "cctv" ->{
                        TainanCctvPage(
                            "台南CCTV",
                            "cctv",
                            params.cctvName,
                            viewModel.tainanCctvList
                        ).let {
                            viewModel.getTainanCctv()
                        }
                    }
                    else ->{
                        MixedInfoPage("即時資訊卡片", "Realtime dashboard", viewModel.uiState)
                        TaichungWaterPage("台中水情", "about the water", viewModel.taichungAirList)
                        TainanCctvPage(
                            "台南CCTV",
                            "cctv",
                            params.cctvName,
                            viewModel.tainanCctvList
                        ).let {
                            viewModel.getTainanCctv()
                        }
                    }
                }
            }
            PageFooter()
        }
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
    return PageSearchParams(topic,cctvName, count)
}

private data class PageSearchParams(val topic: String,val cctvName: String? = null, val count: Int = 10)