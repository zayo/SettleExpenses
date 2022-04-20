package com.nislav.settleexpenses.ui.detail.expense

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.nislav.settleexpenses.databinding.ActivityExpenseDetailBinding
import com.nislav.settleexpenses.ui.ContactWithStateAdapter
import com.nislav.settleexpenses.ui.detail.expense.ExpenseDetailViewModel.ExpenseDetail
import com.nislav.settleexpenses.util.InlinedVMFactory
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Displays contact detail. Use [startActivity] for launch.
 */
@AndroidEntryPoint
class ExpenseDetailActivity : AppCompatActivity() {

    @Inject
    lateinit var factory: ExpenseDetailViewModel.Factory

    private val adapter = ContactWithStateAdapter {
        viewModel.togglePaid(it)
    }

    private var _binding: ActivityExpenseDetailBinding? = null
    private val binding
        get() = requireNotNull(_binding)

    private val viewModel: ExpenseDetailViewModel by viewModels {
        val id = intent.extras?.getLong(EXTRA_EXPENSE_ID) ?: error("Intent is missing important data!")
        InlinedVMFactory { factory.create(id) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityExpenseDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.recycler.adapter = adapter

        lifecycleScope.launch {
            viewModel.expenseDetail
                .flowWithLifecycle(lifecycle)
                .collect(this@ExpenseDetailActivity::displayDetail)
        }
    }

    private fun displayDetail(expense: ExpenseDetail) {
        with(binding) {
            title = expense.name
            price.text = expense.price.toString()
            priceTotal.text = expense.priceTotal.toString()
            adapter.submitList(expense.participants)
            actionConfirmAll.setOnClickListener {
                viewModel.settle()
            }
        }
    }

    companion object {

        /**
         * Starts the [ExpenseDetailActivity] for provided [contactId].
         */
        fun startActivity(context: Context, expenseId: Long) {
            val intent = Intent(context, ExpenseDetailActivity::class.java).apply {
                putExtra(EXTRA_EXPENSE_ID, expenseId)
            }
            context.startActivity(intent)
        }
    }
}

private const val EXTRA_EXPENSE_ID = "expense_id"