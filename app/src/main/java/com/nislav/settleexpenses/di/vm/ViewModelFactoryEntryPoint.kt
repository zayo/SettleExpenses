package com.nislav.settleexpenses.di.vm

import androidx.lifecycle.ViewModel
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Provider

/**
 * [EntryPoint] for [assistedHiltViewModel] to be able to access VM -> VM.Factory map.
 */
@EntryPoint
@InstallIn(SingletonComponent::class)
interface ViewModelFactoryEntryPoint {
    fun factories(): Map<Class<out ViewModel>, Provider<InjectableFactory>>
}