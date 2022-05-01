package com.sample.content

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*
import com.sample.components.CardDark
import com.sample.components.ContainerInSection
import com.sample.components.LinkOnCard
import com.sample.style.*
import com.sample.viewmodel.SellCountUiState

private data class GetStartedCardPresentation(
    val title: String,
    val content: String,
    val links: List<LinkOnCard>? = null
)

private fun getCards(uiState: SellCountUiState) = listOf(
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
        title = "UBike",
        content = uiState.ubike,
        links = listOf(
            LinkOnCard(
                linkText = "Explore the source code",
                linkUrl = "https://github.com/JetBrains/compose-jb/tree/master/examples/web-landing"
            )
        )
    ),
    GetStartedCardPresentation(
        title = "CMS",
        content = uiState.cms,
        links = null
    ),
    GetStartedCardPresentation(
        title = "台積電股價",
        content = uiState.stock
    ),
    GetStartedCardPresentation(
        title = "勤崴股價",
        content = "Loading..."
    ),
    GetStartedCardPresentation(
        title = "台北空氣品質",
        content = "Loading..."
    ),
    GetStartedCardPresentation(
        title = "松山區天氣情況",
        content = "Loading..."
    ),
    GetStartedCardPresentation(
        title = "即時新聞",
        content = "Loading..."
    ),
    GetStartedCardPresentation(
        title = "診所候診",
        content = "Loading..."
    )
)

@Composable
private fun CardContent(text: String) {
    P(attrs = {
        classes(WtTexts.wtText2, WtTexts.wtText2ThemeDark, WtOffsets.wtTopOffset24)
    }) {
        Text(text)
    }
}

@Composable
fun GetStarted(uiState: SellCountUiState) {
    val title = "即時資訊卡片"
    val description = "compose for web + ktor client + kotlin + kotlin/js"

    ContainerInSection(WtSections.wtSectionBgGrayDark) {
        H1(attrs = {
            classes(WtTexts.wtH2, WtTexts.wtH2ThemeDark)
        }) {
            Text(title)
        }

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

        Div(
            attrs = {
                classes(WtRows.wtRow, WtRows.wtRowSizeM, WtOffsets.wtTopOffset24)
            }
        ) {
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
}