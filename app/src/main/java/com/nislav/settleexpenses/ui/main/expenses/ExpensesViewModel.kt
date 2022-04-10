package com.nislav.settleexpenses.ui.main.expenses

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nislav.settleexpenses.domain.ExpensesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

/**
 * ViewModel for the expenses fragment.
 */
@HiltViewModel
class ExpensesViewModel @Inject constructor(
    private val repository: ExpensesRepository
) : ViewModel() {

    val expenses = repository.expenses
        .map { list -> list.sortedBy { it.contacts.all { it.paid } } }
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
}