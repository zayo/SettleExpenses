package com.nislav.settleexpenses.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.nislav.settleexpenses.db.entities.Expense

@Dao
interface ExpenseDao {
    @Query("SELECT * FROM expense")
    suspend fun getAll(): List<Expense>

    @Query("SELECT * FROM expense WHERE id = :expenseId LIMIT 1")
    suspend fun loadById(expenseId: Long): Expense // Not safe, have some faith.

    @Insert
    suspend fun insert(expense: Expense)

    @Delete
    suspend fun delete(expense: Expense)
}
