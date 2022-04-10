package com.nislav.settleexpenses.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.nislav.settleexpenses.db.entities.ExpenseContactRelation

@Dao
interface ExpenseContactDao {

    @Query("SELECT * FROM ExpenseContactRelation WHERE contact_id = :contactId AND expense_id = :expenseId")
    suspend fun get(contactId: Long, expenseId: Long): ExpenseContactRelation

    @Query("SELECT * FROM ExpenseContactRelation WHERE contact_id = :contactId")
    suspend fun getForContact(contactId: Long): List<ExpenseContactRelation>

    @Query("SELECT * FROM ExpenseContactRelation WHERE expense_id = :expenseId")
    suspend fun getForExpense(expenseId: Long): List<ExpenseContactRelation>

    @Insert
    suspend fun insert(relation: List<ExpenseContactRelation>)

    @Update
    suspend fun update(relation: ExpenseContactRelation)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(relation: List<ExpenseContactRelation>)
}