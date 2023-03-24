package com.nislav.settleexpenses.di.vm

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nislav.settleexpenses.util.InlinedVMFactory
import dagger.hilt.EntryPoints

/**
 * Utility for creating [ViewModel]s with their own factories marked with [InjectableFactory].
 * This assumes the concrete [InjectableFactory] is requested through params, which then defines the
 * [ViewModel] to be returned. But setting concrete params to the [InjectableFactory] is completely
 * on caller via [block].
 * It's expected to define mapping through [ViewModelFactory] module.
 *
 * For avoid generating of new instances use `remember(param) { assistedHiltViewModel {...} }`.
 */
@Composable
internal inline fun <reified VMF : InjectableFactory, reified VM : ViewModel> assistedHiltViewModel(
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