package com.nislav.settleexpenses.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

/**
 * Implementation of [ExpensesRepository].
 */
class ExpensesRepositoryImpl : ExpensesRepository {

    private val _expenses: MutableStateFlow<List<Expense>> =
        singletonExpenses

    override val expenses: Flow<List<Expense>>
        get() = _expenses.map { it.toList() }

    override suspend fun load(expenseId: Long): Expense? =
        _expenses.value.find { it.id == expenseId }

    override suspend fun add(expense: Expense) {
        _expenses.value = _expenses.value + expense
    }

    override suspend fun remove(expense: Expense) {
        _expenses.value = _expenses.value - expense
    }

    companion object {
        val contacts = listOf(
            Contact(firstName = "Boris", lastName = "MacLeod"),
            Contact(firstName = "Nicholas", lastName = "Churchill"),
            Contact(firstName = "Bella", lastName = "Chapman"),
            Contact(firstName = "Tim", lastName = "Avery"),
            Contact(firstName = "Rose", lastName = "Lewis"),
            Contact(firstName = "Keith", lastName = "Marshall"),
            Contact(firstName = "Amanda", lastName = "Anderson"),
            Contact(firstName = "Donna", lastName = "Oliver"),
            Contact(firstName = "Alexandra", lastName = "Harris"),
            Contact(firstName = "Abigail", lastName = "Smith"),
            Contact(firstName = "Isaac", lastName = "Hughes"),
            Contact(firstName = "Michael", lastName = "Fraser"),
            Contact(firstName = "Rachel", lastName = "Wilkins"),
            Contact(firstName = "Keith", lastName = "Rampling"),
            Contact(firstName = "Ryan", lastName = "Bower"),
            Contact(firstName = "Mary", lastName = "Hardacre"),
            Contact(firstName = "Andrew", lastName = "Nash")
        )

        // TMP hack until synced through DB
        private val singletonExpenses: MutableStateFlow<List<Expense>> =
            MutableStateFlow(listOf(
                Expense(name = "Nedelni obed", amount = 500L, date = "23. 3. 2022", contacts = contacts.subList(1, 6)),
                Expense(name = "Sobotni obed", amount = 400L, date = "22. 3. 2022", contacts =
                contacts.subList(6, 12)),
                Expense(name = "Patek vecere", amount = 200L, date = "21. 3. 2022", contacts =
                contacts.subList(8, 15)),
                Expense(name = "Ctvrtek snidane", amount = 100L, date = "20. 3. 2022", contacts =
                contacts.subList(14, 16))
            ))
    }
}
