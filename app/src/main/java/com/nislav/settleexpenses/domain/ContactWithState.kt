package com.nislav.settleexpenses.domain

import com.nislav.settleexpenses.db.entities.Contact

/**
 * Represents [Contact] and [paid] state for [Expense] this object is created for.
 */
data class ContactWithState(
    val contact: Contact,
    val paid: Boolean
)
