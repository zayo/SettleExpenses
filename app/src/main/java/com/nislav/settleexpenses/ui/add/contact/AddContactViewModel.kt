package com.nislav.settleexpenses.ui.add.contact

import androidx.lifecycle.ViewModel
import com.nislav.settleexpenses.db.entities.Contact
import com.nislav.settleexpenses.domain.ContactsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * ViewModel for the contacts fragment.
 */
@HiltViewModel
class AddContactViewModel @Inject constructor(
    private val repository: ContactsRepository
) : ViewModel(), AddContactContract {

    override suspend fun addContact(firstName: String, lastName: String) {
        // Intentionally suspend so that screen can close after done.
        repository.add(Contact(firstName, lastName))
    }
}