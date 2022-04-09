package com.nislav.settleexpenses.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.nislav.settleexpenses.databinding.ViewAddContactSheetBinding
import com.nislav.settleexpenses.domain.Contact
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * Adds the contact.
 */
@AndroidEntryPoint
class AddContactBottomSheet : BottomSheetDialogFragment() {

    private val viewModel: AddContactViewModel by viewModels()

    /**
     * Holds the current data before it's saved.
     */
    private var contact = Contact("", "")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ViewAddContactSheetBinding.inflate(inflater, container, false).apply {
        fun refreshStates() {
            val empty = contact.isEmpty
            isCancelable = empty
            actionSave.isEnabled = !empty
        }
        firstNameInput.doAfterTextChanged {
            contact = contact.copy(firstName = it?.toString()?.trim().orEmpty())
            refreshStates()
        }
        lastNameInput.doAfterTextChanged {
            contact = contact.copy(lastName = it?.toString()?.trim().orEmpty())
            refreshStates()
        }
        actionSave.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.addContact(contact).join()
                dismiss()
            }
        }
    }.root

    /**
     * Shows the dialog, provides [TAG] itself.
     */
    fun show(manager: FragmentManager) = super.show(manager, TAG)

    companion object {
        const val TAG = "AddContactBottomSheet"
    }
}

private val Contact.isEmpty
    inline get() = firstName.isEmpty() && lastName.isEmpty()