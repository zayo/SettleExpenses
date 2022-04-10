package com.nislav.settleexpenses.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nislav.settleexpenses.db.dao.ContactDao
import com.nislav.settleexpenses.db.dao.ExpenseContactDao
import com.nislav.settleexpenses.db.dao.ExpenseDao
import com.nislav.settleexpenses.db.entities.Contact
import com.nislav.settleexpenses.db.entities.Expense
import com.nislav.settleexpenses.db.entities.ExpenseContactRelation

@Database(entities = [
    Contact::class,
    Expense::class,
    ExpenseContactRelation::class
], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun contactDao(): ContactDao
    abstract fun expenseDao(): ExpenseDao
    abstract fun expenseContactDao(): ExpenseContactDao

    companion object {
        const val DATABASE_NAME = "app_database"
    }
}