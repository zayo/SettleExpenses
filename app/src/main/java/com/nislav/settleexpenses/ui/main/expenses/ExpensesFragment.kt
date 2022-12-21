package com.nislav.settleexpenses.ui.main.expenses

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
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
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.nislav.settleexpenses.R
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

    private val viewModel: ExpensesViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, state: Bundle?) =
        ComposeView(requireContext()).withThemedContent {
            Scaffold(
                floatingActionButton = {
                    FloatingActionButton(onClick = ::addNewExpense) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = stringResource(R.string.add_new_expense),
                            tint = MaterialTheme.colors.onSecondary
                        )
                    }
                },
                floatingActionButtonPosition = FabPosition.End,
                content = { padding ->
                    val data = viewModel.expenses.collectAsState(emptyList())
                    if (data.value.isEmpty()) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(padding),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = stringResource(R.string.empty_expenses))
                        }
                    } else {
                        Expenses(
                            modifier = Modifier.padding(padding),
                            expenses = data.value,
                            onClick = ::showExpenseDetail
                        )
                    }
                }
            )
        }

    private fun addNewExpense() =
        AddExpenseActivity.startActivity(requireContext())

    private fun showExpenseDetail(expense: Expense) =
        ExpenseDetailActivity.startActivity(requireContext(), expense.expenseId)

}