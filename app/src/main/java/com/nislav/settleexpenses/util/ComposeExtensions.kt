package com.nislav.settleexpenses.util

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.themeadapter.material.MdcTheme

/**
 * Simple method to save one nesting :)
 */
fun ComposeView.withContent(content: @Composable () -> Unit): ComposeView =
    apply { setContent(content) }

/**
 * As [withContent] but saves one nesting more :)
 */
fun ComposeView.withThemedContent(content: @Composable () -> Unit): ComposeView =
    withContent {
        setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        MdcTheme(content = content)
    }

@Preview(
    name = "Light Theme",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Preview(
    name = "DarkTheme",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
annotation class DayNightPreview
