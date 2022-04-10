package com.nislav.settleexpenses.ui.detail.expense

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nislav.settleexpenses.db.entities.Expense
import com.nislav.settleexpenses.domain.ExpenseWithContacts
import com.nislav.settleexpenses.domain.ExpensesRepository
import com.nislav.settleexpenses.domain.name
import com.nislav.settleexpenses.ui.SelectableContactsAdapter.SelectableContact
import com.nislav.settleexpenses.util.normalized
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for the contact detail fragment.
 */
@HiltViewModel
class ExpenseDetailViewModel @Inject constructor(
    private val expensesRepository: ExpensesRepository
) : ViewModel() {

    private val _expense: MutableStateFlow<ExpenseWithContacts?> = MutableStateFlow(null)

    private var expenseId: Long = 0

    /**
     * Holds the current [ExpenseDetail].
     */
    val expenseDetail: Flow<ExpenseDetail>
        get() = _expense.filterNotNull().map { (expense, contactsWithStates) ->
            val contacts = contactsWithStates
                .asSequence()
                .sortedBy { it.contact.name.normalized() }
                .map { SelectableContact(it.contact, it.paid) }
                .sortedBy { it.selected }
                .toList()
            val fraction = expense.amount.div(contacts.size)
            val price = contacts.count { !it.selected } * fraction
            ExpenseDetail(
                expense.name,
                price,
                expense.amount,
                contacts
            )
        }

    /**
     * Loads the [Expense] with provided [id].
     */
    fun loadExpense(id: Long) {
        expenseId = id
        viewModelScope.launch {
            _expense.value = expensesRepository.load(id)
        }
    }

    /**
     * Selects or deselects contact.
     */
    fun togglePaid(selectableContact: SelectableContact) {
        viewModelScope.launch {
            expensesRepository.togglePaid(expenseId, selectableContact.contact.id)
            loadExpense(expenseId)
        }
    }

    /**
     * Marks all participants as settled.
     */
    fun settle() {
        viewModelScope.launch {
            expensesRepository.settleAll(expenseId)
            loadExpense(expenseId)
        }
    }

    /**
     * Represents [Expense] detail precalculated.
     */
    data class ExpenseDetail(
        val name: String,
        val price: Long,
        val priceTotal: Long,
        val participants: List<SelectableContact>
    )
}
