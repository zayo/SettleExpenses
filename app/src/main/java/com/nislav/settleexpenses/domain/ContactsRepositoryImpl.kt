package com.nislav.settleexpenses.domain

import com.nislav.settleexpenses.db.dao.ContactDao
import com.nislav.settleexpenses.db.entities.Contact
import com.nislav.settleexpenses.db.entities.ContactWithExpenses
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Implementation of [ContactsRepository].
 */
class ContactsRepositoryImpl @Inject constructor(
    private val contactDao: ContactDao,
) : ContactsRepository {

    override val contacts: Flow<List<Contact>> =
        contactDao.getAll()

    override fun load(contactId: Long): Flow<ContactWithExpenses> =
        contactDao.getById(contactId)

    override suspend fun add(contact: Contact): Long =
        contactDao.insert(contact)

    override fun loadDebt(contactId: Long): Flow<Long> =
        contactDao.debtOf(contactId).map {
            // In case everything is paid, there will be null returned from the DB.
            // It can be solved in the DB, but this is easier than in SQL lang.
            it ?: 0L
        }
}