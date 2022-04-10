package com.nislav.settleexpenses.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.nislav.settleexpenses.db.entities.Expense
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {
    @Query("SELECT * FROM expense")
    fun getAll(): Flow<List<Expense>>

    @Query("""
        SELECT * FROM Expense WHERE id IN (
           SELECT expense_id FROM ExpenseContactRelation WHERE contact_id = :contactId
        )
        """)
    suspend fun getExpensesForContact(contactId: Long): List<Expense>

    @Query("SELECT * FROM expense WHERE id = :expenseId LIMIT 1")
    suspend fun loadById(expenseId: Long): Expense // Not safe, have some faith.

    @Insert
    suspend fun insert(expense: Expense): Long

    @Delete
    suspend fun delete(expense: Expense)
}
