package com.nislav.settleexpenses.ui.main.expenses

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.nislav.settleexpenses.db.entities.Expense
import com.nislav.settleexpenses.ui.add.expense.AddExpenseActivity
import com.nislav.settleexpenses.ui.detail.expense.ExpenseDetailActivity
import com.nislav.settleexpenses.util.withThemedContent
import dagger.hilt.android.AndroidEntryPoint

/**
 * A fragment displaying Expenses.
 */
@AndroidEntryPoint
class ExpensesFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, state: Bundle?) =
        ComposeView(requireContext()).withThemedContent {
            ExpensesScreen(
                addNewExpense = ::addNewExpense,
                showExpenseDetail = ::showExpenseDetail
            )
        }

    private fun addNewExpense() =
        AddExpenseActivity.startActivity(requireContext())

    private fun showExpenseDetail(expense: Expense) =
        ExpenseDetailActivity.startActivity(requireContext(), expense.expenseId)

}