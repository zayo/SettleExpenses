package com.nislav.settleexpenses.ui.main.expenses

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nislav.settleexpenses.COLOR_DISABLED
import com.nislav.settleexpenses.databinding.ItemContactIconBinding
import com.nislav.settleexpenses.db.entities.Contact
import com.nislav.settleexpenses.db.entities.ContactWithState
import com.nislav.settleexpenses.domain.initials
import com.nislav.settleexpenses.domain.name
import com.nislav.settleexpenses.domain.searchableName
import com.nislav.settleexpenses.getColor

/**
 * Responsible for displaying list of [Contact], as initials.
 */
class InitialsAdapter : RecyclerView.Adapter<InitialsAdapter.InitialsViewHolder>() {

    private val data: MutableList<ContactWithState> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = InitialsViewHolder(
        ItemContactIconBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: InitialsViewHolder, position: Int) =
        holder.bind(data[position])

    override fun getItemCount(): Int = data.size

    @SuppressLint("NotifyDataSetChanged")
    @Synchronized
    fun submitList(list: List<ContactWithState>) {
        data.clear()
        data.addAll(list.sortedBy { it.searchableName })
        notifyDataSetChanged()
    }

    class InitialsViewHolder(
        private val binding: ItemContactIconBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(contact: ContactWithState) {
            with(binding) {
                if (contact.paid) {
                    initialsBg.setColorFilter(COLOR_DISABLED)
                } else {
                    initialsBg.setColorFilter(getColor(contact.name))
                }
                initials.text = contact.initials
            }
        }
    }
}