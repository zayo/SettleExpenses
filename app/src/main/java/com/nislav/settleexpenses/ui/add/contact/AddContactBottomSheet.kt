package com.nislav.settleexpenses.ui.add.contact

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.nislav.settleexpenses.db.entities.Contact
import com.nislav.settleexpenses.util.withThemedContent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * Adds the contact.
 */
@AndroidEntryPoint
class AddContactBottomSheet : BottomSheetDialogFragment() {

    private val viewModel: AddContactViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, state: Bundle?): View =
        ComposeView(requireContext()).withThemedContent {
            AddContact(onSubmit = this@AddContactBottomSheet::onSubmit)
        }

    private fun onSubmit(firstName: String, lastName: String) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.addContact(Contact(firstName, lastName)).join()
            dismiss()
        }
    }

    /**
     * Shows the dialog, provides [TAG] itself.
     */
    fun show(manager: FragmentManager) = super.show(manager, TAG)

    companion object {
        const val TAG = "AddContactBottomSheet"
    }
}
