package com.nislav.settleexpenses.ui.add.expense

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nislav.settleexpenses.db.entities.Contact
import com.nislav.settleexpenses.domain.ContactsRepository
import com.nislav.settleexpenses.domain.Expense
import com.nislav.settleexpenses.domain.ExpensesRepository
import com.nislav.settleexpenses.ui.SelectableContactsAdapter.SelectableContact
import com.nislav.settleexpenses.util.Signal
import com.nislav.settleexpenses.util.normalized
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

/**
 * ViewModel for the contact detail fragment.
 */
@HiltViewModel
class AddExpenseViewModel @Inject constructor(
    private val expensesRepository: ExpensesRepository,
    private val contactsRepository: ContactsRepository
) : ViewModel() {

    private val _contactsSelection = MutableStateFlow(emptySet<Long>())

    private val _query = MutableStateFlow("")

    private val _saveCompletable: CompletableDeferred<Signal> = CompletableDeferred()

    /**
     * holds the current name of the [Expense].
     */
    var expenseName: String = ""

    /**
     * Holds the current price of the [Expense].
     */
    var price: String = ""

    /**
     * Tells how many contacts are currently selected.
     */
    val selectedContacts: Int
        get() = _contactsSelection.value.size

    /**
     * Holds the current contacts, alphabetically sorted, filtered by [query].
     */
    val contacts = contactsRepository.contacts.combine(_query) { contacts, query ->
        val normalizedQuery = query.normalized()
        contacts
            .asSequence()
            .map { it.searchableName to it }
            .filter { (searchableName, _) -> normalizedQuery in searchableName }
            .sortedBy { (searchableName, _) -> searchableName }
            .map { (_, contact) -> contact }
            .toList()
    }.combine(_contactsSelection) { contacts, selection ->
        contacts
            .map {
                val selected = it.id in selection
                SelectableContact(it, selected)
            }
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    /**
     * Holds the search query filter for [contacts].
     */
    var query: String
        get() = _query.value
        set(value) {
            _query.value = value
        }

    /**
     * Holds the state of saving. Once [save] is called, this will eventually gets completed.
     */
    val saveCompletable: Deferred<Signal>
        get() = _saveCompletable

    /**
     * Selects or deselects contact.
     */
    fun toggleSelection(selectableContact: SelectableContact) {
        _contactsSelection.value = if (selectableContact.selected) {
            _contactsSelection.value - selectableContact.contact.id
        } else {
            _contactsSelection.value + selectableContact.contact.id
        }
    }

    fun save() {
        viewModelScope.launch {
            expensesRepository.add(
                Expense(
                    name = expenseName,
                    amount = price.toLong(),
                    date = DATE_FORMAT.format(Date(System.currentTimeMillis())),
                    contacts = contacts.first().filter { it.selected }.map { it.contact }
                )
            )
            _saveCompletable.complete(Signal)
        }
    }
}

private val Contact.searchableName
    get() = "${firstName.normalized()} ${lastName.normalized()}"

private val DATE_FORMAT = SimpleDateFormat.getDateInstance()