package com.nislav.settleexpenses.ui.detail.contact

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nislav.settleexpenses.db.entities.ContactWithExpenses
import com.nislav.settleexpenses.di.vm.InjectableFactory
import com.nislav.settleexpenses.domain.ContactsRepository
import com.nislav.settleexpenses.ui.detail.contact.ContactDetailContract.ContactState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

/**
 * ViewModel for the contact detail fragment.
 */
class ContactDetailViewModel @AssistedInject constructor(
    repository: ContactsRepository,
    @Assisted private val contactId: Long
) : ViewModel(), ContactDetailContract {

    @AssistedFactory
    interface Factory : InjectableFactory {
        fun create(contactId: Long): ContactDetailViewModel
    }

    override val detail: StateFlow<ContactState> = repository.load(contactId)
        .combine(repository.loadDebt(contactId), ::mapData)
        .stateIn(viewModelScope, SharingStarted.Eagerly, Loading)

    private fun mapData(contact: ContactWithExpenses, debt: Long) = Data(
        contact = contact.copy(
            expenses = contact.expenses.sortedBy { it.paid }
        ),
        debt = debt,
    )

    private object Loading : ContactState.Loading

    private data class Data(
        override val contact: ContactWithExpenses,
        override val debt: Long
    ) : ContactState.Data
}