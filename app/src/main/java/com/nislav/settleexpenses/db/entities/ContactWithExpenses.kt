package com.nislav.settleexpenses.db.entities

import androidx.room.Embedded
import androidx.room.Relation

/**
 * Represents [contact] with [expenses]. Uses [ExpenseWithState] database view.
 */
data class ContactWithExpenses(
    @Embedded val contact: Contact,
    @Relation(
        parentColumn = "contactId",
        entityColumn = "contactId",
    )
    val expenses: List<ExpenseWithState>
)
