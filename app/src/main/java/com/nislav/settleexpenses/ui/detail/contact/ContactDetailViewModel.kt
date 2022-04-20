package com.nislav.settleexpenses.ui.detail.contact

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nislav.settleexpenses.db.entities.ContactWithExpenses
import com.nislav.settleexpenses.domain.ContactsRepository
import com.nislav.settleexpenses.ui.detail.contact.ContactDetailViewModel.ContactState.Data
import com.nislav.settleexpenses.ui.detail.contact.ContactDetailViewModel.ContactState.Loading
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

/**
 * ViewModel for the contact detail fragment.
 */
class ContactDetailViewModel @AssistedInject constructor(
    private val repository: ContactsRepository,
    @Assisted private val contactId: Long
) : ViewModel() {

    @AssistedFactory
    interface Factory { fun create(contactId: Long): ContactDetailViewModel }

    val detail: StateFlow<ContactState> = repository.load(contactId).map {
            val debt = repository.calculateDebt(contactId)
            Data(it, debt)
        }.stateIn(viewModelScope, SharingStarted.Eagerly, Loading)

    /**
     * States of Contact loading.
     *
     * @property Loading transition when contact is being loaded.
     * @property Data when data were loaded.
     */
    sealed class ContactState {
        object Loading : ContactState()
        data class Data(val contact: ContactWithExpenses, val debt: Long) : ContactState()
    }
}