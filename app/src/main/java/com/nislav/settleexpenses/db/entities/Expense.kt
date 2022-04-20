package com.nislav.settleexpenses.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Represents expense entry.
 */
@Entity
data class Expense(
    val name: String,
    val amount: Long,
    val date: String,
) {
    @PrimaryKey(autoGenerate = true)
    var expenseId: Long = 0L
}