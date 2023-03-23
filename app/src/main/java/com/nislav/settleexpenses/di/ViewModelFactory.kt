package com.nislav.settleexpenses.di

import com.nislav.settleexpenses.ui.detail.contact.ContactDetailViewModel
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap

@Module
@InstallIn(SingletonComponent::class)
interface ViewModelFactory {

    @Binds
    @IntoMap
    @ViewModelKey(ContactDetailViewModel::class)
    fun registerFactory(fact: ContactDetailViewModel.Factory): Any
}

