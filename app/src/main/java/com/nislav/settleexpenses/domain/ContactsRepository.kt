package com.nislav.settleexpenses.domain

import kotlinx.coroutines.flow.Flow

/**
 * Repository for Contacts and related operation.
 */
interface ContactsRepository {

    /**
     * Holds current contacts.
     */
    val contacts: Flow<List<Contact>>

    /**
     * Loads the contact
     */
    suspend fun load(contactId: Long): Contact?

    /**
     * Adds new [contact].
     */
    suspend fun add(contact: Contact)

    /**
     * Removes existing [contact].
     */
    suspend fun remove(contact: Contact)
}
