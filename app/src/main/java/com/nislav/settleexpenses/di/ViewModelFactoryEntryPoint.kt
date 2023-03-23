package com.nislav.settleexpenses.di

import androidx.lifecycle.ViewModel
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Provider

@EntryPoint
@InstallIn(SingletonComponent::class)
interface ViewModelFactoryEntryPoint {
    fun factories(): Map<Class<out ViewModel>, Provider<Any>>
}