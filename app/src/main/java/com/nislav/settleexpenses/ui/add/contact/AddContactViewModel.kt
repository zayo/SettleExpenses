package com.nislav.settleexpenses.ui.add.contact

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nislav.settleexpenses.db.entities.Contact
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

    suspend fun addContact(firstName: String, lastName: String) {
        repository.add(Contact(firstName, lastName))
    }
}