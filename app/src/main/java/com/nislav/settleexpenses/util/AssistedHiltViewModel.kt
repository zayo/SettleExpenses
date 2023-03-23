package com.nislav.settleexpenses.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nislav.settleexpenses.di.ViewModelFactory
import com.nislav.settleexpenses.di.ViewModelFactoryEntryPoint
import dagger.hilt.EntryPoints

@Composable
internal inline fun <reified VMF, reified VM : ViewModel> assistedHiltViewModel(
    crossinline block: (VMF) -> VM
): VM {
    val owner = checkNotNull(LocalViewModelStoreOwner.current) {
        "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
    }
    val context = checkNotNull(LocalContext.current.applicationContext)
    val entryPoint = EntryPoints.get(context, ViewModelFactoryEntryPoint::class.java)
    return viewModel(viewModelStoreOwner = owner, factory = InlinedVMFactory {
        val provider = entryPoint.factories()[VM::class.java]
            ?: error(
                "Missing Provider<Factory> for ${VM::class.java}. " +
                        "Didn't you forgot to add @Bindings into ${ViewModelFactory::class.java}?"
            )
        block(provider.get() as VMF)
    })
}