package com.nislav.settleexpenses.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nislav.settleexpenses.databinding.ItemContactPickBinding
import com.nislav.settleexpenses.db.entities.Contact
import com.nislav.settleexpenses.domain.initials
import com.nislav.settleexpenses.domain.name
import com.nislav.settleexpenses.getColor
import com.nislav.settleexpenses.ui.SelectableContactsAdapter.SelectableContact
import com.nislav.settleexpenses.ui.SelectableContactsAdapter.SelectableContactsViewHolder

/**
 * Responsible for displaying list of [SelectableContact].
 */
class SelectableContactsAdapter(
    private val itemListener: (SelectableContact) -> Unit
) : ListAdapter<SelectableContact, SelectableContactsViewHolder>(Differ()) {

    private val clickDelegate: (Int) -> Unit = { position -> itemListener(getItem(position)) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        SelectableContactsViewHolder(
            ItemContactPickBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            clickDelegate
        )

    override fun onBindViewHolder(holder: SelectableContactsViewHolder, position: Int) =
        holder.bind(getItem(position))

    class SelectableContactsViewHolder(
        private val binding: ItemContactPickBinding,
        private val positionListener: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                positionListener(adapterPosition)
            }
        }

        @SuppressLint("SetTextI18n")
        fun bind(contact: SelectableContact) {
            with(binding) {
                val contactName = contact.contact.name
                initialsBg.setColorFilter(getColor(contactName))
                initials.text = contact.contact.initials
                name.text = contactName
                checkbox.isChecked = contact.selected
            }
        }
    }

    private class Differ : DiffUtil.ItemCallback<SelectableContact>() {
        override fun areItemsTheSame(oldItem: SelectableContact, newItem: SelectableContact): Boolean =
            oldItem.contact.contactId == newItem.contact.contactId

        override fun areContentsTheSame(oldItem: SelectableContact, newItem: SelectableContact): Boolean =
            oldItem == newItem
    }

    /**
     * Represents [Contact] that can be selected.
     */
    data class SelectableContact(
        val contact: Contact,
        val selected: Boolean = false
    )
}