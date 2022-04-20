package com.nislav.settleexpenses.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.nislav.settleexpenses.db.entities.Contact
import com.nislav.settleexpenses.db.entities.ContactWithExpenses
import com.nislav.settleexpenses.db.entities.ContactWithState
import com.nislav.settleexpenses.db.entities.ExpenseWithContacts
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
}
