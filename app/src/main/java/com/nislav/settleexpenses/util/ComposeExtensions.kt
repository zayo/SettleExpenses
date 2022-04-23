package com.nislav.settleexpenses.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import com.google.android.material.composethemeadapter.MdcTheme

/**
 * Simple method to save one nesting :)
 */
fun ComposeView.withContent(content: @Composable () -> Unit): ComposeView =
    apply { setContent(content) }

/**
 * As [withContent] but saves one nesting more :)
 */
fun ComposeView.withThemedContent(content: @Composable () -> Unit): ComposeView =
    withContent { MdcTheme(content = content) }