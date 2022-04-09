package com.nislav.settleexpenses.ui.main.contacts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.nislav.settleexpenses.databinding.FragmentContactsBinding
import com.nislav.settleexpenses.ui.main.AddContactBottomSheet
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

/**
 * A Fragment displaying Contacts.
 */
@AndroidEntryPoint
class ContactsFragment : Fragment() {

    private val adapter = ContactsAdapter {
        // TODO open detail
        Toast.makeText(requireContext(), "Open detail of [$it]", Toast.LENGTH_SHORT).show()
    }

    private var _binding: FragmentContactsBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val viewModel: ContactsViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, state: Bundle?) =
        FragmentContactsBinding.inflate(inflater, container, false).also { _binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            recycler.adapter = adapter
            empty.root.isVisible = true // initially visible
            fabAdd.setOnClickListener {
                AddContactBottomSheet().show(parentFragmentManager)
            }
        }

        viewModel.contacts
            .flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { data ->
                binding.empty.root.isVisible = data.isEmpty()
                adapter.submitList(data)
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}