package com.nislav.settleexpenses.ui.add.expense

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.google.android.material.textfield.TextInputLayout
import com.nislav.settleexpenses.R
import com.nislav.settleexpenses.databinding.ActivityAddExpenseBinding
import com.nislav.settleexpenses.ui.add.contact.AddContactBottomSheet
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlin.reflect.KMutableProperty0

/**
 * Activity for adding new Expense.
 */
@AndroidEntryPoint
class AddExpenseActivity : AppCompatActivity() {

    private val adapter = SelectableContactsAdapter {
        viewModel.toggleSelection(it)
    }

    private var _binding: ActivityAddExpenseBinding? = null
    private val binding
        get() = requireNotNull(_binding)

    private val viewModel: AddExpenseViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityAddExpenseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        with(binding) {
            recycler.adapter = adapter
            groupNameLayout.linkWith(viewModel::expenseName)
            priceLayout.linkWith(viewModel::price)
            searchLayout.linkWith(viewModel::query)
            actionAdd.setOnClickListener {
                AddContactBottomSheet().show(supportFragmentManager)
            }
            actionSave.setOnClickListener {
                var error = false
                if (viewModel.expenseName.isEmpty()) {
                    groupNameLayout.error = getString(R.string.error_group_name)
                    error = true
                }
                val price = viewModel.price.toLongOrNull() ?: 0
                if (price <= 0) {
                    priceLayout.error = getString(R.string.error_price)
                    error = true
                }
                if (viewModel.selectedContacts == 0) {
                    Toast.makeText(
                        this@AddExpenseActivity,
                        R.string.error_pick_contacts,
                        Toast.LENGTH_LONG
                    ).show()
                    binding.recycler.requestFocus()
                    error = true
                }
                if (!error) {
                    viewModel.save()
                }
            }
        }

        viewModel.contacts
            .flowWithLifecycle(lifecycle)
            .onEach { data ->
                adapter.submitList(data)
            }.launchIn(lifecycleScope)

        lifecycleScope.launch {
            viewModel.saveCompletable.await()
            finish()
        }
    }

    companion object {
        /**
         * Starts the activity for adding new expense.
         */
        fun startActivity(context: Context) {
            context.startActivity(Intent(context, AddExpenseActivity::class.java))
        }
    }
}

/**
 * Links the [EditText] with provided [variable] so all changes are backed in that [variable].
 */
private fun TextInputLayout.linkWith(variable: KMutableProperty0<String>) {
    val editText = requireNotNull(editText)
    editText.setText(variable.get(), TextView.BufferType.EDITABLE)
    editText.doAfterTextChanged {
        error = null
        variable.set(it?.toString()?.trim().orEmpty())
    }
}