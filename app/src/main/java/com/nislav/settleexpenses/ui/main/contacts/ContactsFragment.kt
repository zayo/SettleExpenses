package com.nislav.settleexpenses.ui.main.contacts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.compose.ui.platform.ComposeView
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.nislav.settleexpenses.R
import com.nislav.settleexpenses.db.entities.Contact
import com.nislav.settleexpenses.ui.add.contact.AddContactBottomSheet
import com.nislav.settleexpenses.ui.detail.contact.ContactDetailActivity
import com.nislav.settleexpenses.util.onTextChanged
import com.nislav.settleexpenses.util.withThemedContent
import dagger.hilt.android.AndroidEntryPoint

/**
 * A Fragment displaying Contacts.
 */
@AndroidEntryPoint
class ContactsFragment : Fragment(), MenuProvider {

    private var searchView: SearchView? = null

    private val viewModel: ContactsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requireActivity().addMenuProvider(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, state: Bundle?) =
        ComposeView(requireContext()).withThemedContent {
            ContactsScreen(
                addNewContact = ::addNewContact,
                showContactDetail = ::showContactDetail
            )
        }

    private fun addNewContact() {
        AddContactBottomSheet().show(parentFragmentManager)
    }

    private fun showContactDetail(contact: Contact) {
        ContactDetailActivity.startActivity(requireContext(), contact.contactId)
    }

    override fun onCreateMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_search, menu)
        searchView = menu.findItem(R.id.search).actionView as SearchView
        searchView?.apply {
            setQuery(viewModel.query, false)
            onTextChanged {
                viewModel.query = it
            }
        }
    }

    override fun onMenuItemSelected(menuItem: MenuItem) = false
}