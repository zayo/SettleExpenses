package com.nislav.settleexpenses.db.entities

import androidx.room.Embedded
import androidx.room.Relation

/**
 * Represents [expense] with [contacts]. Uses [ContactWithState] database view.
 */
data class ExpenseWithContacts(
    @Embedded val expense: Expense,
    @Relation(
        parentColumn = "expenseId",
        entityColumn = "expenseId",
    )
    val contacts: List<ContactWithState>
)
