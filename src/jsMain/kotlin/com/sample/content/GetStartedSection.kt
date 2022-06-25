package com.sample.content

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.sample.components.CardContent
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*
import com.sample.components.CardDark
import com.sample.components.ContainerInSection
import com.sample.components.LinkOnCard
import com.sample.model.TaichungAir
import com.sample.model.TainanCctv
import com.sample.model.TravelResponse
import com.sample.style.*
import com.sample.viewmodel.SellCountUiState

data class GetStartedCardPresentation(
    val title: String,
    val content: String,
    val links: List<LinkOnCard>? = null
)

fun getCards(uiState: SellCountUiState) = listOf(
    GetStartedCardPresentation(
        title = "快篩",
        content = uiState.rapidTest,
        links = listOf(
            LinkOnCard(
                linkText = "View tutorial",
                linkUrl = "https://github.com/JetBrains/compose-jb/tree/master/tutorials/Web/Getting_Started"
            )
        )
    ),
    GetStartedCardPresentation(
        title = "台積電股價",
        content = uiState.stock
    ),
    GetStartedCardPresentation(
        title = "診所候診",
        content = uiState.hospitalWaitingId
    ),
    GetStartedCardPresentation(
        title = "台中水情",
        content = uiState.taichungAir
    )
)

@Composable
private fun PageTitle(title: String = "") {
    H1(attrs = {
        classes(WtTexts.wtH2, WtTexts.wtH2ThemeDark)
    }) {
        Text(title)
    }
}

@Composable
private fun PageDescription(description: String = "") {
    Div(attrs = {
        classes(WtRows.wtRowSizeM, WtRows.wtRow, WtOffsets.wtTopOffset24)
    }) {
        Div(attrs = {
            classes(WtCols.wtCol6, WtCols.wtColMd10, WtCols.wtColSm12, WtOffsets.wtTopOffset24)
        }) {
            P(attrs = {
                classes(WtTexts.wtText1)
                style {
                    color(Color("#fff"))
                }
            }) {
                Text(description)
            }
        }
    }
}

@Composable
fun CardStylePage(title: String, description: String, content: @Composable () -> Unit) {
    ContainerInSection(WtSections.wtSectionBgGrayDark) {
        PageTitle(title)
        PageDescription(description)

        Div(
            attrs = {
                classes(WtRows.wtRow, WtRows.wtRowSizeM, WtOffsets.wtTopOffset24)
            }
        ) {
            content()
        }
    }
}

data class CctvUiState(
    var url: String = "",
    var width: CSSSizeValue<CSSUnit.px> = 0.px,
    var height: CSSSizeValue<CSSUnit.px> = 0.px
)