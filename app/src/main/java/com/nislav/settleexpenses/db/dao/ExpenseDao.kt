package com.nislav.settleexpenses.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.nislav.settleexpenses.db.entities.Expense
import com.nislav.settleexpenses.db.entities.ExpenseWithContacts
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {

    @Transaction
    @Query("SELECT * FROM Expense")
    fun getAll(): Flow<List<ExpenseWithContacts>>

    @Transaction
    @Query("SELECT * FROM expense WHERE expenseId = :expenseId LIMIT 1")
    fun getById(expenseId: Long): Flow<ExpenseWithContacts> // Not safe, have some faith.

    @Insert
    suspend fun insert(expense: Expense): Long
}
