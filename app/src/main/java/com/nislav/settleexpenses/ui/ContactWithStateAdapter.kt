package com.nislav.settleexpenses.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nislav.settleexpenses.databinding.ItemContactPickBinding
import com.nislav.settleexpenses.db.entities.ContactWithState
import com.nislav.settleexpenses.domain.initials
import com.nislav.settleexpenses.domain.name
import com.nislav.settleexpenses.util.getColor
import com.nislav.settleexpenses.ui.ContactWithStateAdapter.ContactWithStateViewHolder

/**
 * Responsible for displaying list of [SelectableContact].
 */
class ContactWithStateAdapter(
    private val itemListener: (ContactWithState) -> Unit
) : ListAdapter<ContactWithState, ContactWithStateViewHolder>(Differ()) {

    private val clickDelegate: (Int) -> Unit = { position -> itemListener(getItem(position)) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ContactWithStateViewHolder(
            ItemContactPickBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            clickDelegate
        )

    override fun onBindViewHolder(holder: ContactWithStateViewHolder, position: Int) =
        holder.bind(getItem(position))

    class ContactWithStateViewHolder(
        private val binding: ItemContactPickBinding,
        private val positionListener: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                positionListener(adapterPosition)
            }
        }

        @SuppressLint("SetTextI18n")
        fun bind(contact: ContactWithState) {
            with(binding) {
                val contactName = contact.name
                initialsBg.setColorFilter(getColor(contactName))
                initials.text = contact.initials
                name.text = contactName
                checkbox.isChecked = contact.paid
            }
        }
    }

    private class Differ : DiffUtil.ItemCallback<ContactWithState>() {
        override fun areItemsTheSame(oldItem: ContactWithState, newItem: ContactWithState): Boolean =
            oldItem.contactId == newItem.contactId

        override fun areContentsTheSame(oldItem: ContactWithState, newItem: ContactWithState): Boolean =
            oldItem == newItem
    }
}