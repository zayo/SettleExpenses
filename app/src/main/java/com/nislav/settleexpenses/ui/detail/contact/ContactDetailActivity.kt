package com.nislav.settleexpenses.ui.detail.contact

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.nislav.settleexpenses.databinding.ActivityContactDetailBinding
import com.nislav.settleexpenses.db.entities.ContactWithExpenses
import com.nislav.settleexpenses.domain.name
import com.nislav.settleexpenses.ui.detail.contact.ContactDetailViewModel.ContactState
import com.nislav.settleexpenses.ui.detail.expense.ExpenseDetailActivity
import com.nislav.settleexpenses.util.InlinedVMFactory
import com.nislav.settleexpenses.util.NoOp
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Displays contact detail. Use [startActivity] for launch.
 */
@AndroidEntryPoint
class ContactDetailActivity : AppCompatActivity() {

    @Inject
    lateinit var factory: ContactDetailViewModel.Factory

    private val adapter = ExpensesAdapter {
        ExpenseDetailActivity.startActivity(this, it.expenseId)
    }

    private var _binding: ActivityContactDetailBinding? = null
    private val binding
        get() = requireNotNull(_binding)

    private val viewModel: ContactDetailViewModel by viewModels {
        val id = intent.extras?.getLong(EXTRA_CONTACT_ID) ?: error("Intent is missing important data!")
        InlinedVMFactory { factory.create(id) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityContactDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.recycler.adapter = adapter

        lifecycleScope.launch {
            viewModel.detail
                .flowWithLifecycle(lifecycle)
                .collect {
                    when (it) {
                        is ContactState.Loading -> NoOp // TODO loading if needed
                        is ContactState.Data -> displayDetail(it.contact, it.debt)
                    }
                }
        }
    }

    private fun displayDetail(detail: ContactWithExpenses, owes: Long) {
        with(binding) {
            title = detail.contact.name
            debt.text = owes.toString()
            adapter.submitList(detail.expenses.sortedBy { it.paid })
        }
    }

    companion object {

        /**
         * Starts the [ContactDetailActivity] for provided [contactId].
         */
        fun startActivity(context: Context, contactId: Long) {
            val intent = Intent(context, ContactDetailActivity::class.java).apply {
                putExtra(EXTRA_CONTACT_ID, contactId)
            }
            context.startActivity(intent)
        }
    }
}

private const val EXTRA_CONTACT_ID = "contact_id"