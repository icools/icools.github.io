package com.sample.components

import androidx.compose.runtime.Composable
import com.sample.style.WtCols
import org.jetbrains.compose.web.dom.Text

@Composable
fun LoadingCardDark(){
    CardDark(
        title = "讀取中...",
        wtExtraStyleClasses = listOf(WtCols.wtCol4, WtCols.wtColMd6, WtCols.wtColSm12)
    ){
        Text( "loading...")
    }
}