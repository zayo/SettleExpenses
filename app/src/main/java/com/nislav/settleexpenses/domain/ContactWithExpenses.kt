package com.nislav.settleexpenses.domain

import com.nislav.settleexpenses.db.entities.Contact

/**
 * Represents [Contact] and all his [ExpenseWithState].
 */
data class ContactWithExpenses(
    val contact: Contact,
    val expenses: List<ExpenseWithState>
)