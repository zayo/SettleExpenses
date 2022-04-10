package com.nislav.settleexpenses.domain

import com.nislav.settleexpenses.db.entities.Contact

/**
 * Represents expense entry.
 */
data class Expense(
    val id: Long = System.currentTimeMillis(),
    val name: String,
    val amount: Long,
    val date: String,
    val contacts: Collection<Contact>
)