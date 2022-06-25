package com.sample.components

import androidx.compose.runtime.Composable
import com.sample.TopicEnum
import com.sample.model.Ubike
import com.sample.page.StockPage
import com.sample.page.TainanCctvPage
import com.sample.page.TravelingPage
import com.sample.page.UBikePage
import com.sample.style.WtCols
import com.sample.viewmodel.MainViewModel
import kotlinx.browser.localStorage
import kotlinx.browser.window
import org.jetbrains.compose.web.dom.Text
import org.w3c.dom.get

@Composable
fun ServiceCard(title: String, description: String = "null", topic: TopicEnum) {
    val url = "${window.location}?t=${topic.value}"
    CardDark(
        title = title,
        links = listOf(LinkOnCard("前往", url)),
        wtExtraStyleClasses = listOf(WtCols.wtCol4, WtCols.wtColMd6, WtCols.wtColSm12)
    ) {
        Text(description)
    }
}

@Composable
fun AddTravelingPage(viewModel: MainViewModel) {
    TravelingPage("觀光旅遊", "about 觀光", viewModel.travelingResponse)
}

@Composable
fun AddUBikePage(viewModel: MainViewModel) {
    UBikePage("UBike", "台北剩餘車位", ubikeList = viewModel.ubikeList)
}

@Composable
fun AddTainanCctv(viewModel: MainViewModel, filterName: String?) {
    TainanCctvPage(
        "台南CCTV",
        "cctv",
        filterName,
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