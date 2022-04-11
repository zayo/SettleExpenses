package com.nislav.settleexpenses.ui.main.expenses

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nislav.settleexpenses.COLOR_DISABLED
import com.nislav.settleexpenses.databinding.ItemContactIconBinding
import com.nislav.settleexpenses.db.entities.Contact
import com.nislav.settleexpenses.domain.ContactWithState
import com.nislav.settleexpenses.domain.initials
import com.nislav.settleexpenses.domain.name
import com.nislav.settleexpenses.getColor

/**
 * Responsible for displaying list of [Contact], as initials.
 */
class InitialsAdapter : ListAdapter<ContactWithState, InitialsAdapter.InitialsViewHolder>(Differ2()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = InitialsViewHolder(
        ItemContactIconBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: InitialsViewHolder, position: Int) =
        holder.bind(getItem(position))

    class InitialsViewHolder(
        private val binding: ItemContactIconBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(contact: ContactWithState) {
            with(binding) {
                if (contact.paid) {
                    initialsBg.setColorFilter(COLOR_DISABLED)
                } else {
                    initialsBg.setColorFilter(getColor(contact.contact.name))
                }
                initials.text = contact.contact.initials
            }
        }
    }
}

private class Differ2 : DiffUtil.ItemCallback<ContactWithState>() {
    override fun areItemsTheSame(oldItem: ContactWithState, newItem: ContactWithState): Boolean =
        oldItem.contact.id == newItem.contact.id

    override fun areContentsTheSame(oldItem: ContactWithState, newItem: ContactWithState): Boolean =
        oldItem == newItem
}
