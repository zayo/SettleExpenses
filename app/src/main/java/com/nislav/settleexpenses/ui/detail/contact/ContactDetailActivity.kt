package com.nislav.settleexpenses.ui.detail.contact

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.nislav.settleexpenses.R
import com.nislav.settleexpenses.databinding.ActivityContactDetailBinding
import com.nislav.settleexpenses.db.entities.ContactWithExpenses
import com.nislav.settleexpenses.domain.name
import com.nislav.settleexpenses.ui.detail.contact.ContactDetailViewModel.ContactState
import com.nislav.settleexpenses.ui.detail.expense.ExpenseDetailActivity
import com.nislav.settleexpenses.util.InlinedVMFactory
import com.nislav.settleexpenses.util.NoOp
import dagger.hilt.android.AndroidEntryPoint
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
        val id =
            intent.extras?.getLong(EXTRA_CONTACT_ID) ?: error("Intent is missing important data!")
        InlinedVMFactory { factory.create(id) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ContactDetailScreen(
                vm = viewModel,
                onNavigateUp = ::onNavigateUp,
                onExpenseClicked = { item ->
                    ExpenseDetailActivity.startActivity(this, item.expenseId)
                }
            )
        }

//        _binding = ActivityContactDetailBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        setSupportActionBar(binding.toolbar)
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//
//        binding.recycler.adapter = adapter
//
//        lifecycleScope.launch {
//            viewModel.detail
//                .flowWithLifecycle(lifecycle)
//                .collect {
//                    when (it) {
//                        is ContactState.Loading -> NoOp // TODO loading if needed
//                        is ContactState.Data -> displayDetail(it.contact, it.debt)
//                    }
//                }
//        }
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