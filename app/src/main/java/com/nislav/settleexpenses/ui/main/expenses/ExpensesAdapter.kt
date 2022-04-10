package com.nislav.settleexpenses.ui.main.expenses

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nislav.settleexpenses.databinding.ItemExpenseBinding
import com.nislav.settleexpenses.domain.ExpenseWithContacts

/**
 * Responsible for displaying list of [ExpenseWithContacts].
 */
class ExpensesAdapter(
    private val itemListener: (ExpenseWithContacts) -> Unit
) : ListAdapter<ExpenseWithContacts, ExpensesAdapter.ExpensesViewHolder>(Differ()) {

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

        private val adapter = InitialsAdapter()

        init {
            binding.root.setOnClickListener {
                positionListener(adapterPosition)
            }
            binding.recycler.adapter = adapter
        }

        @SuppressLint("SetTextI18n")
        fun bind(expense: ExpenseWithContacts) {
            with(binding) {
                date.text = expense.expense.date
                name.text = expense.expense.name
                adapter.submitList(expense.contacts.map { it.contact })
                amount.text = expense.expense.amount.toString()
            }
        }
    }
}

private class Differ : DiffUtil.ItemCallback<ExpenseWithContacts>() {
    override fun areItemsTheSame(oldItem: ExpenseWithContacts, newItem: ExpenseWithContacts): Boolean =
        oldItem.expense.id == newItem.expense.id

    override fun areContentsTheSame(oldItem: ExpenseWithContacts, newItem: ExpenseWithContacts): Boolean =
        oldItem == newItem
}