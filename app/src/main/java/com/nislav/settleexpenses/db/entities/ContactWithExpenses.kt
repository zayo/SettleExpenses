package com.nislav.settleexpenses.db.entities

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class ContactWithExpenses(
    @Embedded val contact: Contact,
    @Relation(
        parentColumn = "contactId",
        entityColumn = "contactId",
    )
    val expenses: List<ExpenseWithState>
)
