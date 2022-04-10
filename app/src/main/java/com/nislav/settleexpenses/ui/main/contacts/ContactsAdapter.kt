package com.nislav.settleexpenses.ui.main.contacts

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nislav.settleexpenses.databinding.ItemContactBinding
import com.nislav.settleexpenses.db.entities.Contact
import com.nislav.settleexpenses.domain.initials
import com.nislav.settleexpenses.domain.name
import com.nislav.settleexpenses.getColor

/**
 * Responsible for displaying list of [Contact].
 */
class ContactsAdapter(
    private val itemListener: (Contact) -> Unit
) : ListAdapter<Contact, ContactsAdapter.ContactsViewHolder>(Differ()) {

    private val clickDelegate: (Int) -> Unit = { position -> itemListener(getItem(position)) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ContactsViewHolder(
            ItemContactBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            clickDelegate
        )

    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) =
        holder.bind(getItem(position))

    class ContactsViewHolder(
        private val binding: ItemContactBinding,
        private val positionListener: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                positionListener(adapterPosition)
            }
        }

        @SuppressLint("SetTextI18n")
        fun bind(contact: Contact) {
            with(binding) {
                initialsBg.setColorFilter(getColor(contact))
                initials.text = contact.initials
                name.text = contact.name
            }
        }
    }
}

private class Differ : DiffUtil.ItemCallback<Contact>() {
    override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean =
        oldItem == newItem
}