package com.nislav.settleexpenses.di

import android.content.Context
import androidx.room.Room
import com.nislav.settleexpenses.db.AppDatabase
import com.nislav.settleexpenses.db.dao.ContactDao
import com.nislav.settleexpenses.db.dao.ExpenseContactDao
import com.nislav.settleexpenses.db.dao.ExpenseDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, AppDatabase.DATABASE_NAME).build()

    @Provides
    fun provideContactDao(database: AppDatabase): ContactDao = database.contactDao()

    @Provides
    fun provideExpenseDao(database: AppDatabase): ExpenseDao = database.expenseDao()

    @Provides
    fun provideExpenseContactDao(database: AppDatabase): ExpenseContactDao = database.expenseContactDao()
}