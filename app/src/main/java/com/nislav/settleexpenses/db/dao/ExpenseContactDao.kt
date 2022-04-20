package com.nislav.settleexpenses.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.nislav.settleexpenses.db.entities.ExpenseContactRelation

@Dao
interface ExpenseContactDao {

    @Insert
    suspend fun insert(relation: List<ExpenseContactRelation>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(relation: ExpenseContactRelation)

    @Query("UPDATE ExpenseContactRelation SET paid = 1 WHERE expenseId = :expenseId")
    suspend fun settleAll(expenseId: Long)
}