package com.nislav.settleexpenses.db.entities

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class ExpenseWithContacts(
    @Embedded val expense: Expense,
    @Relation(
        parentColumn = "expenseId",
        entityColumn = "expenseId",
    )
    val contacts: List<ContactWithState>
)
