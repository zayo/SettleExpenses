package com.nislav.settleexpenses.db.entities

import androidx.room.DatabaseView

/**
 * Database view that destructs many-to-many relation and can be used instead of
 * [ExpenseContactRelation] for joining contacts with expense.
 */
@DatabaseView(
    """
        SELECT s.expenseId, c.contactId, c.firstName, c.lastName, s.paid 
            FROM Contact c 
            INNER JOIN ExpenseContactRelation s 
            ON c.contactId = s.contactId 
    """
)
data class ContactWithState(
    val expenseId: Long,
    val contactId: Long,
    val firstName: String,
    val lastName: String,
    val paid: Boolean
)
