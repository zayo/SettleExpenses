package com.nislav.settleexpenses.di.vm

import com.nislav.settleexpenses.ui.detail.contact.ContactDetailViewModel
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap

/**
 * Serves for the [assistedHiltViewModel] utility to know, what VM is produced by what factory.
 * Uses the basic [Binds] to map VM to the respective []
 */
@Module
@InstallIn(SingletonComponent::class)
interface ViewModelFactory {

    @Binds
    @IntoMap
    @ViewModelKey(ContactDetailViewModel::class)
    fun registerFactory(fact: ContactDetailViewModel.Factory): InjectableFactory
}

