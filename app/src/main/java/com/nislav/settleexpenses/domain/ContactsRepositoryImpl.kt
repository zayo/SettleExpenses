package com.nislav.settleexpenses.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

/**
 * Implementation of [ContactsRepository].
 */
class ContactsRepositoryImpl : ContactsRepository {

    private val _contacts: MutableStateFlow<List<Contact>> =
        singletonContacts

    override val contacts: Flow<List<Contact>>
        get() = _contacts.map { it.toList() }

    override suspend fun load(contactId: Long): Contact? =
        _contacts.value.find { it.id == contactId }

    override suspend fun add(contact: Contact) {
        _contacts.value = _contacts.value + contact
    }

    override suspend fun remove(contact: Contact) {
        _contacts.value = _contacts.value - contact
    }

    companion object {
        // TMP hack until synced through DB
        private val singletonContacts: MutableStateFlow<List<Contact>> =
            MutableStateFlow(emptyList())
    }
}