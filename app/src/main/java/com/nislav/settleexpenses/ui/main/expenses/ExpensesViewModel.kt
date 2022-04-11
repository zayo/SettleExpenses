package com.nislav.settleexpenses.ui.main.expenses

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nislav.settleexpenses.domain.ExpenseWithContacts
import com.nislav.settleexpenses.domain.ExpensesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

/**
 * ViewModel for the expenses fragment.
 */
@HiltViewModel
class ExpensesViewModel @Inject constructor(
    private val repository: ExpensesRepository
) : ViewModel() {

    suspend fun loadExpenses(): List<ExpenseWithContacts> =
        repository.getExpenses().sortedBy { it.contacts.all { it.paid } }
}