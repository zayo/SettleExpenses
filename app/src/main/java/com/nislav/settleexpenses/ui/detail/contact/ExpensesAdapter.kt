package com.nislav.settleexpenses.ui.detail.contact

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nislav.settleexpenses.R
import com.nislav.settleexpenses.databinding.ItemExpenseSimpleBinding
import com.nislav.settleexpenses.db.entities.ExpenseWithState
import com.nislav.settleexpenses.ui.detail.contact.ExpensesAdapter.ExpensesViewHolder

/**
 * Responsible for displaying list of [ExpenseWithState].
 */
class ExpensesAdapter(
    private val itemListener: (ExpenseWithState) -> Unit
) : ListAdapter<ExpenseWithState, ExpensesViewHolder>(Differ()) {

    private val clickDelegate: (Int) -> Unit = { position -> itemListener(getItem(position)) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ExpensesViewHolder(
            ItemExpenseSimpleBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            clickDelegate
        )

    override fun onBindViewHolder(holder: ExpensesViewHolder, position: Int) =
        holder.bind(getItem(position))

    class ExpensesViewHolder(
        private val binding: ItemExpenseSimpleBinding,
        private val positionListener: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                positionListener(adapterPosition)
            }
        }

        fun bind(expense: ExpenseWithState) {
            with(binding) {
                name.text = expense.name
                if (expense.paid) {
                    root.setBackgroundResource(R.drawable.bg_expense_settled)
                } else {
                    root.setBackgroundResource(R.drawable.bg_expense)
                }
            }
        }
    }
}

private class Differ : DiffUtil.ItemCallback<ExpenseWithState>() {
    override fun areItemsTheSame(oldItem: ExpenseWithState, newItem: ExpenseWithState): Boolean =
        oldItem.expenseId == newItem.expenseId

    override fun areContentsTheSame(oldItem: ExpenseWithState, newItem: ExpenseWithState): Boolean =
        oldItem == newItem
}