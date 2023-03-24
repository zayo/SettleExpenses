package com.nislav.settleexpenses.ui.add.contact

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.nislav.settleexpenses.util.withThemedContent
import dagger.hilt.android.AndroidEntryPoint

/**
 * Adds the contact.
 */
@AndroidEntryPoint
class AddContactBottomSheet : BottomSheetDialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, state: Bundle?): View =
        ComposeView(requireContext()).withThemedContent {
            AddContactScreen {
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
