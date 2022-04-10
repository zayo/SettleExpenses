package com.nislav.settleexpenses.domain

import com.nislav.settleexpenses.db.entities.Expense

/**
 * Represents [Expense] and [paid] state for [Contact] this object is created for.
 */
data class ExpenseWithState(
    val expense: Expense,
    val paid: Boolean
)
