package com.nislav.settleexpenses.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nislav.settleexpenses.domain.Contact
import com.nislav.settleexpenses.domain.ContactsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for the contacts fragment.
 */
@HiltViewModel
class AddContactViewModel @Inject constructor(
    private val repository: ContactsRepository
) : ViewModel() {

    fun addContact(contact: Contact): Job {
        // TODO publish state.
        return viewModelScope.launch {
            repository.add(contact)
        }
    }
}