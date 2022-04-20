package com.nislav.settleexpenses.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

fun <T> InlinedVMFactory(block: () -> T): ViewModelProvider.Factory =
    object : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return block() as T
        }
    }