package com.sample.page

import androidx.compose.runtime.Composable
import com.sample.components.CardContent
import com.sample.components.CardDark
import com.sample.content.CardStylePage
import com.sample.content.GetStartedCardPresentation
import com.sample.model.TaichungAir
import com.sample.style.WtCols

@Composable
fun TaichungAirPage(title: String, description: String, airList: List<TaichungAir> = emptyList()) {
    CardStylePage(title, description) {
        airList.filterIndexed { index, _ ->
            index < 9
        }.map {
            GetStartedCardPresentation(
                title = "${it.name}-${it.item}",
                content = "${it.value}(${it.status})"
            )
        }.forEach {
            CardDark(
                title = it.title,
                links = it.links,
                wtExtraStyleClasses = listOf(WtCols.wtCol4, WtCols.wtColMd6, WtCols.wtColSm12)
            ) {
                CardContent(it.content)
            }
        }
    }
}