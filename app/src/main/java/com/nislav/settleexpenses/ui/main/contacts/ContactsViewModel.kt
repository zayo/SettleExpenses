package com.nislav.settleexpenses.ui.main.contacts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nislav.settleexpenses.db.entities.Contact
import com.nislav.settleexpenses.domain.ContactsRepository
import com.nislav.settleexpenses.util.normalized
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

/**
 * ViewModel for the contacts fragment.
 */
@HiltViewModel
class ContactsViewModel @Inject constructor(
    repository: ContactsRepository
) : ViewModel() {

    private val _query = MutableStateFlow("")

    /**
     * Holds the search query filter for [contacts].
     */
    var query: String
        get() = _query.value
        set(value) {
            _query.value = value
        }

    /**
     * Holds the current contacts, alphabetically sorted, filtered by [query].
     */
    val contacts = repository.contacts.combine(_query) { contacts, query ->
        val normalizedQuery = query.normalized()
        contacts
            .asSequence()
            .map { it.searchableName to it }
            .filter { (searchableName, _) -> query in searchableName }
            .sortedBy { (searchableName, _) -> searchableName }
            .map { (_, contact) -> contact }
            .toList()
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
}

private val Contact.searchableName
    get() = "${firstName.normalized()} ${lastName.normalized()}"
