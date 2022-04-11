package com.nislav.settleexpenses.domain

import com.nislav.settleexpenses.db.dao.ContactDao
import com.nislav.settleexpenses.db.dao.ExpenseContactDao
import com.nislav.settleexpenses.db.dao.ExpenseDao
import com.nislav.settleexpenses.db.entities.Contact
import com.nislav.settleexpenses.db.entities.Expense
import com.nislav.settleexpenses.db.entities.ExpenseContactRelation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
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

    override suspend fun getExpenses(): List<ExpenseWithContacts> = expenseDao.getAll().map {
            load(it.id)
        }

    // FIXME: far from OK, needs better DB schema
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
        val relations = contacts.map {
            ExpenseContactRelation(expenseId, it.id)
        }
        statesDao.insert(relations)
    }

    override suspend fun togglePaid(expenseId: Long, contactId: Long) =
        withContext(Dispatchers.Default) {
            val state = statesDao.get(contactId, expenseId)
            statesDao.update(state.copy(paid = !state.paid))
        }

    override suspend fun settleAll(expenseId: Long) =
        withContext(Dispatchers.Default) {
            val states = statesDao.getForExpense(expenseId).map { it.copy(paid = true) }
            statesDao.update(states)
        }
}

