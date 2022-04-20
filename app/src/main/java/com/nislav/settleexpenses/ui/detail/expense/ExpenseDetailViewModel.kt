package com.nislav.settleexpenses.ui.detail.expense

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nislav.settleexpenses.db.entities.ContactWithState
import com.nislav.settleexpenses.db.entities.Expense
import com.nislav.settleexpenses.domain.ExpensesRepository
import com.nislav.settleexpenses.domain.name
import com.nislav.settleexpenses.util.normalized
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * ViewModel for the contact detail fragment.
 */
class ExpenseDetailViewModel @AssistedInject constructor(
    private val expensesRepository: ExpensesRepository,
    @Assisted private val expenseId: Long
) : ViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(expenseId: Long): ExpenseDetailViewModel
    }

    /**
     * Holds the current [ExpenseDetail].
     */
    val expenseDetail: Flow<ExpenseDetail> =
        expensesRepository.load(expenseId).map { expense ->
            val contacts = expense.contacts
                .asSequence()
                .sortedBy { it.name.normalized() }
                .sortedBy { it.paid }
                .toList()
            val fraction = expense.expense.amount.div(contacts.size.coerceAtLeast(1))
            val price = contacts.count { !it.paid } * fraction
            ExpenseDetail(
                expense.expense.name,
                price,
                expense.expense.amount,
                contacts
            )
        }.stateIn(viewModelScope, SharingStarted.Eagerly, ExpenseDetail("", 0L, 0L, emptyList()))

    /**
     * Selects or deselects contact.
     */
    fun togglePaid(contact: ContactWithState) {
        viewModelScope.launch {
            expensesRepository.togglePaid(expenseId, contact.contactId, !contact.paid)
        }
    }

    /**
     * Marks all participants as settled.
     */
    fun settle() {
        viewModelScope.launch {
            expensesRepository.settleAll(expenseId)
        }
    }

    /**
     * Represents [Expense] detail precalculated.
     */
    data class ExpenseDetail(
        val name: String,
        val price: Long,
        val priceTotal: Long,
        val participants: List<ContactWithState>
    )
}
