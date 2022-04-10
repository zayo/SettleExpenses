package com.nislav.settleexpenses.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nislav.settleexpenses.domain.Contact
import com.nislav.settleexpenses.domain.ContactsRepository
import com.nislav.settleexpenses.ui.detail.ContactDetailViewModel.ContactState.Data
import com.nislav.settleexpenses.ui.detail.ContactDetailViewModel.ContactState.Failed
import com.nislav.settleexpenses.ui.detail.ContactDetailViewModel.ContactState.Init
import com.nislav.settleexpenses.ui.detail.ContactDetailViewModel.ContactState.Loading
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for the contact detail fragment.
 */
@HiltViewModel
class ContactDetailViewModel @Inject constructor(
    private val repository: ContactsRepository
) : ViewModel() {

    private val _state: MutableStateFlow<ContactState> =
        MutableStateFlow(Init)

    val contactState: StateFlow<ContactState> =
        _state.asStateFlow()

    fun loadContact(id: Long) {
        viewModelScope.launch {
            _state.emit(Loading)
            val contact = repository.load(id)
            val state = contact?.let { Data(it) }
                ?: Failed(id)
            _state.emit(state)
        }
    }

    /**
     * States of Contact loading.
     *
     * @property Init when [loadContact] needs to be called to initiate flow.
     * @property Loading transition when contact is being loaded.
     * @property Data when data were loaded successfully.
     * @property Failed when data were unable to load due to non-existing id passed
     * into [loadContact].
     */
    sealed class ContactState {
        object Init : ContactState()
        object Loading : ContactState()
        data class Data(val contact: Contact) : ContactState()
        data class Failed(val id: Long) : ContactState()
    }
}