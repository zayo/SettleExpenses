package com.nislav.settleexpenses.ui.detail.contact

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.themeadapter.material.MdcTheme
import com.nislav.settleexpenses.R
import com.nislav.settleexpenses.db.entities.ExpenseWithState
import com.nislav.settleexpenses.domain.name
import com.nislav.settleexpenses.ui.Samples
import com.nislav.settleexpenses.ui.detail.contact.ContactDetailViewModel.ContactState
import com.nislav.settleexpenses.util.DayNightPreview

@Composable
fun ContactDetailScreen(
    vm: ContactDetailViewModel = hiltViewModel(),
    onNavigateUp: () -> Unit,
    onExpenseClicked: (ExpenseWithState) -> Unit,
) {

    val state by vm.detail.collectAsStateWithLifecycle()
    val title by remember {
        derivedStateOf {
            (state as? ContactState.Data)?.contact?.contact?.name
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = title ?: stringResource(id = R.string.contact_detail)) },
                navigationIcon = {
                    IconButton(onClick = { onNavigateUp() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when (val localState = state) {
                is ContactState.Loading -> CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )

                is ContactState.Data -> ContactDetail(
                    modifier = Modifier.fillMaxSize(),
                    state = localState,
                    onExpenseClicked = onExpenseClicked
                )
            }
        }
    }
}

@Composable
private fun ContactDetail(
    modifier: Modifier = Modifier,
    state: ContactState.Data,
    onExpenseClicked: (ExpenseWithState) -> Unit = {},
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OwesComponent(debt = state.debt)
        ExpensesComponent(
            data = state.contact.expenses.sortedBy { it.paid },
            onExpenseClicked = onExpenseClicked,
        )
    }
}

@Composable
private fun OwesComponent(debt: Long) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = stringResource(id = R.string.owes_you),
            style = MaterialTheme.typography.caption
        )
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = "$$debt",
            style = MaterialTheme.typography.h4
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun ExpensesComponent(
    modifier: Modifier = Modifier,
    data: List<ExpenseWithState>,
    onExpenseClicked: (ExpenseWithState) -> Unit = {},
) {
    if (data.isEmpty()) {
        return
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = stringResource(id = R.string.expenses),
            style = MaterialTheme.typography.caption
        )
        LazyColumn {
            items(data) { item ->
                Card(
                    onClick = {
                        onExpenseClicked(item)
                    },
                    elevation = 4.dp,
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        modifier = Modifier.padding(16.dp),
                        text = "$${item.amount}",
                        style = MaterialTheme.typography.button
                    )
                }
            }
        }
    }
}

@DayNightPreview
@Composable
private fun Preview() {
    MdcTheme {
        val state = ContactState.Data(
            contact= Samples.contactWithExpenses,
            debt = 12345L,
        )
        ContactDetail(state = state)
    }
}