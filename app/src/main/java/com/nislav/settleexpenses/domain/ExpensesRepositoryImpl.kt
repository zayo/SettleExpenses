package com.nislav.settleexpenses.domain

import com.nislav.settleexpenses.db.dao.ContactDao
import com.nislav.settleexpenses.db.dao.ExpenseContactDao
import com.nislav.settleexpenses.db.dao.ExpenseDao
import com.nislav.settleexpenses.db.entities.Contact
import com.nislav.settleexpenses.db.entities.Expense
import com.nislav.settleexpenses.db.entities.ExpenseContactRelation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Implementation of [ExpensesRepository].
 */
class ExpensesRepositoryImpl @Inject constructor(
    private val contactDao: ContactDao,
    private val expenseDao: ExpenseDao,
    private val statesDao: ExpenseContactDao,
) : ExpensesRepository {

    override val expenses: Flow<List<ExpenseWithContacts>>
        get() = expenseDao.getAll().map {
            it.map { load(it.id) }
        }

    override suspend fun load(expenseId: Long): ExpenseWithContacts =
        withContext(Dispatchers.Default) {
            val expense = async { expenseDao.loadById(expenseId) }
            val states = async { statesDao.getForExpense(expenseId) }
            val contacts = async { contactDao.getContactsForExpense(expenseId) }
            ExpenseWithContacts(
                expense.await(),
                contacts.await().map { contact ->
                    ContactWithState(
                        contact,
                        states.await().find { it.contactId == contact.id }?.paid == true)
                }
            )
        }

    override suspend fun add(expense: Expense, contacts: List<Contact>) {
        val expenseId = expenseDao.insert(expense)
        contacts.forEach {
            statesDao.insert(ExpenseContactRelation(
                expenseId = expenseId,
                contactId = it.id
            ))
        }
    }

    override suspend fun togglePaid(expenseId: Long, contactId: Long) =
        withContext(Dispatchers.Default) {
            val state = statesDao.get(contactId, expenseId)
            statesDao.insert(state.copy(paid = !state.paid))
        }

    override suspend fun settleAll(expenseId: Long) =
        withContext(Dispatchers.Default) {
            val states = statesDao.getForExpense(expenseId)
            states.forEach {
                statesDao.insert(it.copy(paid = true))
            }
        }
}

