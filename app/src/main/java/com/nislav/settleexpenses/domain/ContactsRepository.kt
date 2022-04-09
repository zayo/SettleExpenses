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
     * Adds new [contact].
     */
    suspend fun add(contact: Contact)

    /**
     * Removes existing [contact].
     */
    suspend fun remove(contact: Contact)
}
