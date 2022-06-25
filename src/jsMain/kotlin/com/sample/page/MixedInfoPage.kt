package com.sample.page

import androidx.compose.runtime.Composable
import com.sample.components.CardContent
import com.sample.components.CardDark
import com.sample.content.CardStylePage
import com.sample.model.Cms
import com.sample.style.WtCols

@Composable
fun MixedInfoPage(title: String, description: String, cms: Cms) {
    CardStylePage(title, description) {
        if(cms.locations.isEmpty()){
            CardDark(
                title = "讀取中,有點耐心...",
                wtExtraStyleClasses = listOf(WtCols.wtCol4, WtCols.wtColMd6, WtCols.wtColSm12)
            ) {
                CardContent("在等等喔")
            }
            return@CardStylePage
        }

        cms.locations.forEach {
            CardDark(
                title = "${it.cmsName}(${it.cmsId})",
                wtExtraStyleClasses = listOf(WtCols.wtCol4, WtCols.wtColMd6, WtCols.wtColSm12)
            ) {
                CardContent(it.cmsMsg)
            }
        }
    }
}