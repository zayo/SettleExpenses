package com.nislav.settleexpenses.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(primaryKeys = ["expense_id", "contact_id"])
data class ExpenseContactRelation(
    @ColumnInfo(name = "expense_id")
    val expenseId: Long,
    @ColumnInfo(name = "contact_id")
    val contactId: Long,
    val paid: Boolean = false,
)