package com.nislav.settleexpenses.ui.detail.expense

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nislav.settleexpenses.db.entities.Contact
import com.nislav.settleexpenses.domain.Expense
import com.nislav.settleexpenses.domain.ExpensesRepository
import com.nislav.settleexpenses.ui.SelectableContactsAdapter.SelectableContact
import com.nislav.settleexpenses.util.normalized
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for the contact detail fragment.
 */
@HiltViewModel
class ExpenseDetailViewModel @Inject constructor(
    private val expensesRepository: ExpensesRepository
) : ViewModel() {

    private val _contactsSelection = MutableStateFlow(emptySet<Long>())
    private val _expense: MutableStateFlow<Expense?> = MutableStateFlow(null)

    /**
     * Holds the current [ExpenseDetail].
     */
    val expenseDetail: Flow<ExpenseDetail>
        get() = _expense.filterNotNull().combine(_contactsSelection) { expense, selection ->
            val contacts = expense.contacts
                .asSequence()
                .sortedBy { it.searchableName }
                .map { SelectableContact(it, it.id in selection) }
                .sortedBy { it.selected }
                .toList()
            val fraction = expense.amount.div(expense.contacts.size)
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
        viewModelScope.launch {
            _expense.value = expensesRepository.load(id)
                ?: error("Expense with id= [$id] not found!")
        }
    }

    /**
     * Selects or deselects contact.
     */
    fun toggleSelection(selectableContact: SelectableContact) {
        _contactsSelection.value = if (selectableContact.selected) {
            _contactsSelection.value - selectableContact.contact.id
        } else {
            _contactsSelection.value + selectableContact.contact.id
        }
    }

    /**
     * Marks all participants as settled.
     */
    fun settle() {
        viewModelScope.launch {
            _contactsSelection.value = requireNotNull(_expense.value).contacts.map { it.id }.toSet()
        }
    }

    /**
     * Represents [Expense] detail.
     */
    data class ExpenseDetail(
        val name: String,
        val price: Long,
        val priceTotal: Long,
        val participants: List<SelectableContact>
    )
}

private val Contact.searchableName
    get() = "${firstName.normalized()} ${lastName.normalized()}"
