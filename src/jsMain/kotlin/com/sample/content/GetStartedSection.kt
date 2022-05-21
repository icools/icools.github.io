package com.sample.content

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*
import com.sample.components.CardDark
import com.sample.components.ContainerInSection
import com.sample.components.LinkOnCard
import com.sample.model.TaichungAir
import com.sample.model.TainanCctv
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
        title = "臺北市即時交通訊息",
        content = uiState.taipeiTraffic
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
private fun CardContent(text: String) {
    P(attrs = {
        classes(WtTexts.wtText2, WtTexts.wtText2ThemeDark, WtOffsets.wtTopOffset24)
    }) {
        Text(text)
    }
}

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
private fun CardStylePage(title: String, description: String, content: @Composable () -> Unit) {
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

@Composable
fun TaichungWaterPage(title: String, description: String, airList: List<TaichungAir> = emptyList()) {
    CardStylePage(title, description) {
        airList.map {
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

@Composable
fun TainanCctvPage(title: String, description: String, cctvList: List<TainanCctv> = emptyList()) {
    CardStylePage(title, description) {
        cctvList.filter {
            it.url != null
        }.map {
            GetStartedCardPresentation(
                title = "${it.positionName}",
                content = "${it.lat}${it.lon}",
                links = listOf(LinkOnCard(it.positionName, it.url ?: ""))
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