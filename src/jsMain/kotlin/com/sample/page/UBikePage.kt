package com.sample.page

import androidx.compose.runtime.Composable
import com.sample.components.CardDark
import com.sample.components.LoadingCardDark
import com.sample.content.CardStylePage
import com.sample.model.Ubike
import com.sample.style.WtCols
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text

@Composable
fun UBikePage(
    title: String,
    description: String,
    filterName: String? = null,
    ubikeList: List<Ubike> = emptyList()
) {
    CardStylePage("$title(${ubikeList.size})", description) {
        if(ubikeList.isEmpty()){
            LoadingCardDark()
            return@CardStylePage
        }

        ubikeList.forEach { item ->
            val name = item.stationName.replace("YouBike2.0_","")
            CardDark(
                title = name,
                wtExtraStyleClasses = listOf(WtCols.wtCol4, WtCols.wtColMd6, WtCols.wtColSm12)
            ){
                Div {
                    Text("Total:${item.sbi}/${item.totalCount} at ${item.sarea},${item.ar}")
                }
                Div {
                    Text("Update:${item.updateTime}")
                }
            }
        }
    }
}