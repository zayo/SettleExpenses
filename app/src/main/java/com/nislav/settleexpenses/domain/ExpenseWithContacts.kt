package com.nislav.settleexpenses.domain

import com.nislav.settleexpenses.db.entities.Contact
import com.nislav.settleexpenses.db.entities.Expense

/**
 * Represents [Contact] and all his [ExpenseWithState].
 */
data class ExpenseWithContacts(
    val expense: Expense,
    val contacts: List<ContactWithState>
)