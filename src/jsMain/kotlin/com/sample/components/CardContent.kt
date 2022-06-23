package com.sample.components

import androidx.compose.runtime.Composable
import com.sample.style.WtOffsets
import com.sample.style.WtTexts
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text

@Composable
fun CardContent(text: String) {
    P(attrs = {
        classes(WtTexts.wtText2, WtTexts.wtText2ThemeDark, WtOffsets.wtTopOffset24)
    }) {
        Text(text)
    }
}