package com.nislav.settleexpenses.domain

import com.nislav.settleexpenses.db.entities.Contact
import kotlinx.coroutines.flow.Flow

/**
 * Repository for [Contacts] and related operation.
 */
interface ContactsRepository {

    /**
     * Holds current contacts.
     */
    val contacts: Flow<List<Contact>>

    /**
     * Loads the contact
     */
    suspend fun load(contactId: Long): ContactWithExpenses

    /**
     * Adds new [contact].
     */
    suspend fun add(contact: Contact): Long

    /**
     * Removes existing [contact].
     */
    @Deprecated(message = "Makes no sense, would break everything.", level = DeprecationLevel.ERROR)
    suspend fun remove(contact: Contact)

    /**
     * Calculates debt of [contactId].
     */
    suspend fun calculateDebt(contactId: Long): Long
}
