package com.nislav.settleexpenses.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity

/**
 * Represents many-to-many relation between [Contact] and [Expense].
 * Use indirectly through [ExpenseWithState] and [ContactWithState].
 */
@Entity(primaryKeys = ["expenseId", "contactId"])
data class ExpenseContactRelation(
    @ColumnInfo(index = true)
    val expenseId: Long,
    @ColumnInfo(index = true)
    val contactId: Long,
    val paid: Boolean = false,
)