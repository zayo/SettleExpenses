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

    override suspend fun remove(expense: Expense) {
        _expenses.value = _expenses.value - expense
    }

    companion object {
        // TMP hack until synced through DB
        private val singletonExpenses: MutableStateFlow<List<Expense>> =
            MutableStateFlow(emptyList())
    }
}