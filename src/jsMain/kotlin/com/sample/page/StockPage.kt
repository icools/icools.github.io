package com.sample.page

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import com.sample.components.CardDark
import com.sample.content.CardStylePage
import com.sample.content.CctvUiState
import com.sample.model.TainanCctv
import com.sample.model.stock.StockNewTo
import com.sample.style.WtCols
import org.jetbrains.compose.web.css.height
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.width
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Img
import org.jetbrains.compose.web.dom.Text

@Composable
fun StockPage(
    title: String,
    description: String,
    stockList: List<StockNewTo> = emptyList()
) {
    CardStylePage(title, description) {
        if(stockList.isEmpty()){
            CardDark(
                title = "讀取中...",
                wtExtraStyleClasses = listOf(WtCols.wtCol4, WtCols.wtColMd6, WtCols.wtColSm12)
            ){
                Text( "讀取中...")
            }
            return@CardStylePage
        }

        stockList.forEach { item ->
            CardDark(
                title = "${item.company} ${item.code}",
                wtExtraStyleClasses = listOf(WtCols.wtCol4, WtCols.wtColMd6, WtCols.wtColSm12)
            ){
                Div {
                    Text("日期: ${item.approvedDate}")
                }
                Div{
                    Text("負責人: ${item.chairman}, 證卷商:${item.underwriter}")
                }
                item.note.takeIf { it.isNotEmpty() }?.let{
                    Text("備註:${it}")
                }
            }
        }
    }
}