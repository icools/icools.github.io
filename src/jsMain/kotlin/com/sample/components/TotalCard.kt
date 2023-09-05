package com.sample.components

import androidx.compose.runtime.Composable
import com.sample.TopicEnum
import com.sample.page.StockPage
import com.sample.page.TainanCctvPage
import com.sample.page.TravelingPage
import com.sample.page.UBikePage
import com.sample.style.WtCols
import com.sample.viewmodel.MainViewModel
import kotlinx.browser.window
import org.jetbrains.compose.web.dom.Text

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