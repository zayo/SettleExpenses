package com.nislav.settleexpenses.domain

import kotlinx.coroutines.flow.Flow

/**
 * Repository for [Expense] related operations.
 */
interface ExpensesRepository {

    /**
     * Holds list of all expenses.
     */
    val expenses: Flow<List<Expense>>

    /**
     * Loads the Expense based on [expenseId].
     */
    suspend fun load(expenseId: Long): Expense?

    /**
     * Removes existing [expense].
     */
    suspend fun remove(expense: Expense)
}