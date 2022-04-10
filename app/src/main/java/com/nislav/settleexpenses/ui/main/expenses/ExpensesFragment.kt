package com.nislav.settleexpenses.ui.main.expenses

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.nislav.settleexpenses.databinding.FragmentExpensesBinding
import com.nislav.settleexpenses.ui.add.expense.AddExpenseActivity
import com.nislav.settleexpenses.ui.detail.expense.ExpenseDetailActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

/**
 * A fragment displaying Expenses.
 */
@AndroidEntryPoint
class ExpensesFragment : Fragment() {

    private val adapter = ExpensesAdapter {
        ExpenseDetailActivity.startActivity(requireContext(), it.id)
    }

    private var _binding: FragmentExpensesBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val viewModel: ExpensesViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, state: Bundle?) =
        FragmentExpensesBinding.inflate(inflater, container, false).also { _binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            recycler.adapter = adapter
            empty.root.isVisible = true // initially visible
            fabAdd.setOnClickListener {
                AddExpenseActivity.startActivity(requireContext())
            }
        }

        viewModel.expenses
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