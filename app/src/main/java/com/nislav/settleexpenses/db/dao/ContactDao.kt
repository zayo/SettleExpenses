package com.nislav.settleexpenses.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.nislav.settleexpenses.db.entities.Contact
import com.nislav.settleexpenses.db.entities.ContactWithExpenses
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactDao {

    @Query("SELECT * FROM Contact")
    fun getAll(): Flow<List<Contact>>

    @Transaction
    @Query("SELECT * FROM Contact WHERE contactId = :contactId LIMIT 1")
    fun getById(contactId: Long): Flow<ContactWithExpenses> // Not safe, have some faith.

    @Insert
    suspend fun insert(contact: Contact): Long

    @Query("""
        SELECT SUM(e.amount / c.participants) as debt
        FROM Expense e 
        INNER JOIN ExpenseContactRelation r ON r.expenseId = e.expenseId
        INNER JOIN (
            -- Calculate participants of each expense
            SELECT expenseId, COUNT(*) as participants
            FROM ExpenseContactRelation
            GROUP BY (expenseId)
            -- not expected, but to avoid zero division
            HAVING participants > 0
        ) c ON r.expenseId = c.expenseId
        WHERE r.contactId = :contactId AND r.paid = 0 -- false
    """)
    fun debtOf(contactId: Long): Flow<Long?>
}
