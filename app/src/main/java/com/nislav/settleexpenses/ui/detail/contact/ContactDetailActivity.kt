package com.nislav.settleexpenses.ui.detail.contact

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.nislav.settleexpenses.ui.detail.expense.ExpenseDetailActivity
import com.nislav.settleexpenses.util.InlinedVMFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Displays contact detail. Use [startActivity] for launch.
 */
@AndroidEntryPoint
class ContactDetailActivity : AppCompatActivity() {

    @Inject
    lateinit var factory: ContactDetailViewModel.Factory

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