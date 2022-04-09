package com.nislav.settleexpenses.di

import com.nislav.settleexpenses.domain.ContactsRepository
import com.nislav.settleexpenses.domain.ExpensesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    fun provideExpensesRepository() = ExpensesRepository()

    @Provides
    fun provideContactsRepository() = ContactsRepository()
}