package com.nislav.settleexpenses.ui.main.contacts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nislav.settleexpenses.domain.ContactsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

/**
 * ViewModel for the contacts fragment.
 */
@HiltViewModel
class ContactsViewModel @Inject constructor(
    repository: ContactsRepository
) : ViewModel() {

    val contacts = repository.contacts
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
}