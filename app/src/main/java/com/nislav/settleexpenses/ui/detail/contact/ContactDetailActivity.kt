package com.nislav.settleexpenses.ui.detail.contact

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.nislav.settleexpenses.ui.detail.expense.ExpenseDetailActivity
import dagger.hilt.android.AndroidEntryPoint

/**
 * Displays contact detail. Use [startActivity] for launch.
 */
@AndroidEntryPoint
class ContactDetailActivity : AppCompatActivity() {

    private val id by lazy {
        intent.extras?.getLong(EXTRA_CONTACT_ID) ?: error("Intent is missing important data!")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val contactId = intent.extras?.getLong(EXTRA_CONTACT_ID)
            ?: error("Intent is missing important data!")

        setContent {
            ContactDetailScreen(
                contactId = contactId,
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