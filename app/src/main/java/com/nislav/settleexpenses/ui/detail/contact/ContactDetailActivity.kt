package com.nislav.settleexpenses.ui.detail.contact

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.nislav.settleexpenses.databinding.ActivityContactDetailBinding
import com.nislav.settleexpenses.domain.ContactWithExpenses
import com.nislav.settleexpenses.ui.detail.contact.ContactDetailViewModel.ContactState
import com.nislav.settleexpenses.util.NoOp
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * Displays contact detail. Use [startActivity] for launch.
 */
@AndroidEntryPoint
class ContactDetailActivity : AppCompatActivity() {

    private var _binding: ActivityContactDetailBinding? = null
    private val binding
        get() = requireNotNull(_binding)

    private val viewModel: ContactDetailViewModel by viewModels()

    private val detailId by lazy {
        intent.extras?.getLong(EXTRA_CONTACT_ID) ?: error("Intent is missing important data!")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityContactDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel.loadContact(detailId)

        lifecycleScope.launch {
            viewModel.contactState
                .flowWithLifecycle(lifecycle)
                .collect {
                    when (it) {
                        is ContactState.Init -> NoOp
                        is ContactState.Loading -> NoOp // TODO loading if needed
                        is ContactState.Data -> displayDetail(it.contact)
                    }
                }
        }
    }

    private fun displayDetail(detail: ContactWithExpenses) {
        with(binding) {
            firstName.text = detail.contact.firstName
            lastName.text = detail.contact.lastName
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