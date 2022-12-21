package com.nislav.settleexpenses.di

import com.nislav.settleexpenses.domain.ContactsRepository
import com.nislav.settleexpenses.domain.ContactsRepositoryImpl
import com.nislav.settleexpenses.domain.ExpensesRepository
import com.nislav.settleexpenses.domain.ExpensesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun provideExpensesRepository(impl: ExpensesRepositoryImpl): ExpensesRepository

    @Binds
    fun provideContactsRepository(impl: ContactsRepositoryImpl): ContactsRepository
}