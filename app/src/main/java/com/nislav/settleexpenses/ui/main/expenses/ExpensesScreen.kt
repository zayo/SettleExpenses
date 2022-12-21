package com.nislav.settleexpenses.ui.main.expenses

import android.content.res.Configuration
import androidx.annotation.Size
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.themeadapter.material.MdcTheme
import com.nislav.settleexpenses.util.COLOR_DISABLED
import com.nislav.settleexpenses.R
import com.nislav.settleexpenses.db.entities.ContactWithState
import com.nislav.settleexpenses.db.entities.Expense
import com.nislav.settleexpenses.db.entities.ExpenseWithContacts
import com.nislav.settleexpenses.domain.initials
import com.nislav.settleexpenses.domain.searchableName
import com.nislav.settleexpenses.util.getColor
import com.nislav.settleexpenses.ui.Samples

@Composable
fun Expenses(
    modifier: Modifier = Modifier,
    @Size(min = 1)
    expenses: List<ExpenseWithContacts>,
    onClick: (Expense) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(
            items = expenses,
            key = { it.expense.expenseId }
        ) { item ->
            Expense(item, onClick)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
fun LazyItemScope.Expense(
    expenseWithContacts: ExpenseWithContacts,
    onClick: (Expense) -> Unit
) {
    val expense = expenseWithContacts.expense
    val contacts = expenseWithContacts.contacts.sortedBy { it.searchableName }
    val paid = contacts.all { it.paid }
    val background = if (paid) Color(0xFFF3FFEB) else MaterialTheme.colors.surface
    Card(
        modifier = Modifier
            .animateItemPlacement()
            .fillMaxWidth(),
        backgroundColor = background,
        elevation = 4.dp,
        onClick = { onClick(expense) },
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = expense.name,
                style = MaterialTheme.typography.body2,
            )
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(contacts) { contactWithState ->
                    ContactIcon(contactWithState)
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = expense.date,
                    style = MaterialTheme.typography.caption,
                    color = Color.Gray
                )
                Text(
                    text = stringResource(R.string.currency_dollar) + expense.amount,
                    style = MaterialTheme.typography.body2.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = Color.Gray
                )
            }
        }
    }
}

@Composable
fun ContactIcon(contact: ContactWithState) {
    val initials = contact.initials
    val color = Color(if (contact.paid) COLOR_DISABLED else getColor(initials))
    Box(
        modifier = Modifier
            .size(24.dp)
            .background(
                color = color,
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = initials,
            style = MaterialTheme.typography.body2.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 11.sp
            ),
            color = Color.White,
        )
    }
}

@Preview(name = "Expenses light theme")
@Preview(
    name = "Expenses dark theme",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
fun Preview() {
    MdcTheme {
        Expenses(expenses = Samples.expenses)
    }
}