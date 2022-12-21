package com.nislav.settleexpenses.ui.main.contacts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
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
            Scaffold(
                floatingActionButton = {
                    FloatingActionButton(onClick = ::addNewContact) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = stringResource(R.string.add_new_contact),
                            tint = MaterialTheme.colors.onSecondary
                        )
                    }
                },
                floatingActionButtonPosition = FabPosition.End,
                content = { padding ->
                    val data = viewModel.contacts.collectAsState(emptyList())
                    if (data.value.isEmpty()) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(padding),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = stringResource(R.string.empty_contacts))
                        }
                    } else {
                        Contacts(
                            modifier = Modifier.padding(padding),
                            contacts = data.value,
                            onClick = ::showContactDetail
                        )
                    }
                }
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