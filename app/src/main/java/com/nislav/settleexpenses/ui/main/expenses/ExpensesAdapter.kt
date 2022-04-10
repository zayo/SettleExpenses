package com.nislav.settleexpenses.ui.main.expenses

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nislav.settleexpenses.databinding.ItemExpenseBinding
import com.nislav.settleexpenses.domain.Expense

/**
 * Responsible for displaying list of [Expense].
 */
class ExpensesAdapter(
    private val itemListener: (Expense) -> Unit
) : ListAdapter<Expense, ExpensesAdapter.ExpensesViewHolder>(Differ()) {

    private val clickDelegate: (Int) -> Unit = { position -> itemListener(getItem(position)) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ExpensesViewHolder(
            ItemExpenseBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            clickDelegate
        )

    override fun onBindViewHolder(holder: ExpensesViewHolder, position: Int) =
        holder.bind(getItem(position))

    class ExpensesViewHolder(
        private val binding: ItemExpenseBinding,
        private val positionListener: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                positionListener(adapterPosition)
            }
        }

        @SuppressLint("SetTextI18n")
        fun bind(expense: Expense) {
            with(binding) {
                date.text = expense.date
                name.text = expense.name
                amount.text = expense.amount.toString()
            }
        }
    }
}

private class Differ : DiffUtil.ItemCallback<Expense>() {
    override fun areItemsTheSame(oldItem: Expense, newItem: Expense): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Expense, newItem: Expense): Boolean =
        oldItem == newItem
}