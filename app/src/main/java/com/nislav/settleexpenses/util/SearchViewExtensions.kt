package com.nislav.settleexpenses.util

import androidx.appcompat.widget.SearchView

/**
 * Sets simplified on text changed listener for [SearchView].
 */
fun SearchView.onTextChanged(block: (String) -> Unit) =
    setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean = true

        override fun onQueryTextChange(newText: String?): Boolean {
            block(newText.orEmpty())
            return true
        }
    })
