package com.nislav.settleexpenses.domain

import com.nislav.settleexpenses.db.entities.Contact
import com.nislav.settleexpenses.db.entities.ContactWithExpenses
import kotlinx.coroutines.flow.Flow

/**
 * Repository for [Contact] and related operation.
 */
interface ContactsRepository {

    /**
     * Holds current contacts.
     */
    val contacts: Flow<List<Contact>>

    /**
     * Loads the contact
     */
    fun load(contactId: Long): Flow<ContactWithExpenses>

    /**
     * Adds new [contact].
     */
    suspend fun add(contact: Contact): Long

    /**
     * Calculates debt of [contactId].
     */
    fun loadDebt(contactId: Long): Flow<Long>
}
