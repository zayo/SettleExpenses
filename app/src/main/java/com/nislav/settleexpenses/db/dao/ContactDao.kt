package com.nislav.settleexpenses.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.nislav.settleexpenses.db.entities.Contact
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactDao {
    @Query("SELECT * FROM contact")
    fun getAll(): Flow<List<Contact>>

    @Query("SELECT * FROM contact WHERE id = :contactId LIMIT 1")
    suspend fun loadById(contactId: Long): Contact // Not safe, have some faith.

    @Insert
    suspend fun insert(contact: Contact)

    @Delete
    suspend fun delete(contact: Contact)
}
