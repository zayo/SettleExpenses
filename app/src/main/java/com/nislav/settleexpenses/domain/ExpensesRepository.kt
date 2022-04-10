package com.nislav.settleexpenses.domain

import com.nislav.settleexpenses.db.entities.Contact
import com.nislav.settleexpenses.db.entities.Expense
import kotlinx.coroutines.flow.Flow

/**
 * Repository for [Expense] related operations.
 */
interface ExpensesRepository {

    /**
     * Holds list of all [ExpenseWithContacts].
     */
    val expenses: Flow<List<ExpenseWithContacts>>

    /**
     * Loads the [ExpenseWithContacts] based on [expenseId].
     */
    suspend fun load(expenseId: Long): ExpenseWithContacts

    /**
     * Adds the [Expense].
     */
    suspend fun add(expense: Expense, contacts: List<Contact>)

    /**
     * Switches the paid state of the [contactId] for the [expenseId].
     */
    suspend fun togglePaid(expenseId: Long, contactId: Long)

    /**
     * Marks all states as paid of all contacts for [expenseId].
     */
    suspend fun settleAll(expenseId: Long)
}