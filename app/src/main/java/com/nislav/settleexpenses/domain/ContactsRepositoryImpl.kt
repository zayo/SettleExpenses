package com.nislav.settleexpenses.domain

import com.nislav.settleexpenses.db.dao.ContactDao
import com.nislav.settleexpenses.db.dao.ExpenseContactDao
import com.nislav.settleexpenses.db.dao.ExpenseDao
import com.nislav.settleexpenses.db.entities.Contact
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Implementation of [ContactsRepository].
 */
class ContactsRepositoryImpl @Inject constructor(
    private val contactDao: ContactDao,
    private val expenseDao: ExpenseDao,
    private val statesDao: ExpenseContactDao,
) : ContactsRepository {

    override val contacts: Flow<List<Contact>>
        get() = contactDao.getAll()

    // FIXME: far from OK, needs better DB schema
    override suspend fun load(contactId: Long): ContactWithExpenses =
        withContext(Dispatchers.Default) {
            val contact = async { contactDao.loadById(contactId) }
            val states = async { statesDao.getForContact(contactId) }
            val expenses = async { expenseDao.getExpensesForContact(contactId) }
            ContactWithExpenses(
                contact.await(),
                expenses.await().map { expense ->
                    ExpenseWithState(
                        expense,
                        states.await().find { it.expenseId == expense.id }?.paid == true)
                }
            )
        }

    override suspend fun add(contact: Contact): Long =
        contactDao.insert(contact)

    // FIXME: far from OK, needs better DB schema, like "archive" contact, but keep for
    //  consistency of other objects/relations.
    override suspend fun remove(contact: Contact) =
        contactDao.delete(contact)

    // FIXME: far from OK, needs better DB schema
    override suspend fun calculateDebt(contactId: Long): Long {
        val expenses = expenseDao.getExpensesForContact(contactId)
        return expenses.sumOf {
            val contacts = statesDao.getForExpense(it.id)
            val price = it.amount.div(contacts.size)
            price.takeIf { contacts.any { it.contactId == contactId && !it.paid } } ?: 0L
        }
    }
}