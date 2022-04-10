package com.nislav.settleexpenses.domain

import com.nislav.settleexpenses.db.dao.ContactDao
import com.nislav.settleexpenses.db.entities.Contact
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Implementation of [ContactsRepository].
 */
class ContactsRepositoryImpl @Inject constructor(
    private val dao: ContactDao
) : ContactsRepository {

    override val contacts: Flow<List<Contact>>
        get() = dao.getAll()

    override suspend fun load(contactId: Long): Contact =
        dao.loadById(contactId)

    override suspend fun add(contact: Contact) =
        dao.insert(contact)

    override suspend fun remove(contact: Contact) =
        dao.delete(contact)
}