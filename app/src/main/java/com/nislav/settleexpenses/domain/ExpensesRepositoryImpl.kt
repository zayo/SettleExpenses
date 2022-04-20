package com.nislav.settleexpenses.domain

import com.nislav.settleexpenses.db.dao.ContactDao
import com.nislav.settleexpenses.db.dao.ExpenseContactDao
import com.nislav.settleexpenses.db.dao.ExpenseDao
import com.nislav.settleexpenses.db.entities.Contact
import com.nislav.settleexpenses.db.entities.Expense
import com.nislav.settleexpenses.db.entities.ExpenseContactRelation
import com.nislav.settleexpenses.db.entities.ExpenseWithContacts
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Implementation of [ExpensesRepository].
 */
class ExpensesRepositoryImpl @Inject constructor(
    private val expenseDao: ExpenseDao,
    private val statesDao: ExpenseContactDao,
) : ExpensesRepository {

    override val expenses: Flow<List<ExpenseWithContacts>> =
        expenseDao.getAll()

    override fun load(expenseId: Long): Flow<ExpenseWithContacts> =
        expenseDao.getById(expenseId)

    override suspend fun add(expense: Expense, contacts: List<Contact>) {
        val expenseId = expenseDao.insert(expense)
        val relations = contacts.map {
            ExpenseContactRelation(expenseId, it.contactId)
        }
        statesDao.insert(relations)
    }

    override suspend fun togglePaid(expenseId: Long, contactId: Long, newValue: Boolean) =
        statesDao.insert(ExpenseContactRelation(expenseId, contactId, newValue))

    override suspend fun settleAll(expenseId: Long) =
        statesDao.settleAll(expenseId)
}

