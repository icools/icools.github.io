package com.sample.page

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import com.sample.components.CardDark
import com.sample.content.CardStylePage
import com.sample.content.CctvUiState
import com.sample.model.TainanCctv
import com.sample.style.WtCols
import org.jetbrains.compose.web.css.height
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.width
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Img
import org.jetbrains.compose.web.dom.Text

// TODO add loading
@Composable
fun TainanCctvPage(
    title: String,
    description: String,
    cctvFilterName: String? = null,
    cctvList: List<TainanCctv> = emptyList()
) {
    CardStylePage(title, description) {
        cctvList.filter {
            if(cctvFilterName == null){
                it.url != null
            }else{
                it.url != null && it.positionName == cctvFilterName
            }
        }.filter {
            if(cctvFilterName == null){
                true
            }else{
                it.positionName == cctvFilterName
            }
        }.forEach { cctv ->
            val uiState = mutableStateOf(CctvUiState())
            CardDark(
                title = cctv.positionName,
                wtExtraStyleClasses = listOf(WtCols.wtCol4, WtCols.wtColMd6, WtCols.wtColSm12)
            ){
                Button (attrs = {
                    onClick {
                        uiState.value = uiState.value.copy(
                            url = cctv.url ?: "",
                            width = 320.px,
                            height = 200.px
                        )
                    }
                }){
                    Text("View")
                }
                Img(src = uiState.value.url, attrs = {
                    style {
                        width(uiState.value.width)
                        height(uiState.value.height)
                    }
                })
            }
        }
    }
}