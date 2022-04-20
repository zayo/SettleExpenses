package com.nislav.settleexpenses.domain

import com.nislav.settleexpenses.db.dao.ContactDao
import com.nislav.settleexpenses.db.entities.Contact
import com.nislav.settleexpenses.db.entities.ContactWithExpenses
import kotlinx.coroutines.flow.Flow
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

    // FIXME: far from OK, needs better DB schema
    override suspend fun calculateDebt(contactId: Long): Long {
//        val expenses = expenseDao.getExpensesForContact(contactId)
//        return expenses.sumOf {
//            val contacts = statesDao.getForExpense(it.id)
//            val price = it.amount.div(contacts.size)
//            price.takeIf { contacts.any { it.contactId == contactId && !it.paid } } ?: 0L
//        }
        return 666L
    }
}