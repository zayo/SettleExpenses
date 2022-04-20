package com.nislav.settleexpenses.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(primaryKeys = ["expenseId", "contactId"])
data class ExpenseContactRelation(
    @ColumnInfo(index = true)
    val expenseId: Long,
    @ColumnInfo(index = true)
    val contactId: Long,
    val paid: Boolean = false,
)