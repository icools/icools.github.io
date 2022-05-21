package com.sample

import com.sample.components.Layout
import com.sample.components.MainContentLayout
import com.sample.content.*
import com.sample.style.AppStylesheet
import com.sample.viewmodel.MainViewModel
import kotlinx.browser.document
import org.jetbrains.compose.web.css.Style
import org.jetbrains.compose.web.renderComposable

fun main() {
    val viewModel = MainViewModel()
    renderComposable(rootElementId = "root") {
        Style(AppStylesheet)

        Layout {
            Header()
            MainContentLayout {
                //IntroCustom(viewModel.uiState)
                //Intro()
                //ComposeWebLibraries()
                //CodeSamples()
                //JoinUs()
                MixedInfoPage("即時資訊卡片", "Realtime dashboard", viewModel.uiState)
                TaichungWaterPage("台中水情", "about the water", viewModel.taichungAirList)
                TainanCctvPage("台南CCTV","cctv",viewModel.tainanCctvList).let{
                    viewModel.getTainanCctv()
                }
            }
            PageFooter()
        }
    }
}