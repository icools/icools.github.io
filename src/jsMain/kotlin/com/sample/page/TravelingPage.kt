package com.sample.page

import androidx.compose.runtime.Composable
import com.sample.TopicEnum
import com.sample.components.CardDark
import com.sample.content.CardStylePage
import com.sample.content.GetStartedCardPresentation
import com.sample.model.TravelResponse
import com.sample.style.WtCols
import kotlinx.browser.sessionStorage
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.width
import org.jetbrains.compose.web.dom.Img
import org.jetbrains.compose.web.dom.Text
import org.w3c.dom.get
import org.w3c.dom.set

@Composable
fun TravelingPage(title: String, description: String, travelResponse: TravelResponse) {
    CardStylePage(title, description) {
        GetStartedCardPresentation(
            title = "Travel",
            content = "${travelResponse.total}"
        )

        travelResponse.data.forEach {
            CardDark(
                title = it.name,
                wtExtraStyleClasses = listOf(WtCols.wtCol4, WtCols.wtColMd6, WtCols.wtColSm12)
            ){
                Text(it.email)
                Text(it.introduction)
                Text("建議停留時間:${it.staytime}")
                it.images?.firstOrNull()?.let{ img ->
                    img.src?.let{ imgSrc ->
                        Img(src= imgSrc, attrs = {
                            style {
                                width(300.px)
                            }
                        })
                    }
                }
            }
        }
    }
}