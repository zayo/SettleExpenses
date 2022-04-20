package com.nislav.settleexpenses.db.entities

import androidx.room.DatabaseView

@DatabaseView(
    """
        SELECT s.contactId, e.expenseId, e.name, e.amount, e.date, s.paid
            FROM Expense e 
            INNER JOIN ExpenseContactRelation s 
            ON e.expenseId = s.expenseId 
    """
)
data class ExpenseWithState(
    val contactId: Long,
    val expenseId: Long,
    val name: String,
    val amount: Long,
    val date: String,
    val paid: Boolean
)
