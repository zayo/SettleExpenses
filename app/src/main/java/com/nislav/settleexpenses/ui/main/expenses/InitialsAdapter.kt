package com.nislav.settleexpenses.ui.main.expenses

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nislav.settleexpenses.databinding.ItemContactIconBinding
import com.nislav.settleexpenses.domain.Contact
import com.nislav.settleexpenses.getColor

/**
 * Responsible for displaying list of [Contact], as initials.
 */
class InitialsAdapter : ListAdapter<Contact, InitialsAdapter.InitialsViewHolder>(Differ2()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = InitialsViewHolder(
        ItemContactIconBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: InitialsViewHolder, position: Int) =
        holder.bind(getItem(position))

    class InitialsViewHolder(
        private val binding: ItemContactIconBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(contact: Contact) {
            with(binding) {
                initialsBg.setColorFilter(getColor(contact))
                initials.text = contact.initials
            }
        }
    }
}

private val Contact.initials
    inline get() = "${firstName.first()}${lastName.first()}".uppercase()

private class Differ2 : DiffUtil.ItemCallback<Contact>() {
    override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean =
        oldItem == newItem
}
