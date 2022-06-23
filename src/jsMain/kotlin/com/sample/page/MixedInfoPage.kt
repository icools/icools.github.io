package com.sample.page

import androidx.compose.runtime.Composable
import com.sample.components.CardContent
import com.sample.components.CardDark
import com.sample.content.CardStylePage
import com.sample.content.getCards
import com.sample.style.WtCols
import com.sample.viewmodel.SellCountUiState

@Composable
fun MixedInfoPage(title: String, description: String, uiState: SellCountUiState) {
    CardStylePage(title, description) {
        getCards(uiState).forEach {
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