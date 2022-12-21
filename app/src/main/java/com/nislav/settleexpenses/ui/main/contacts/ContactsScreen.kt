package com.nislav.settleexpenses.ui.main.contacts

import android.content.res.Configuration
import androidx.annotation.Size
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.themeadapter.material.MdcTheme
import com.nislav.settleexpenses.db.entities.Contact
import com.nislav.settleexpenses.domain.initials
import com.nislav.settleexpenses.domain.name
import com.nislav.settleexpenses.util.getColor
import com.nislav.settleexpenses.ui.Samples

@Composable
fun Contacts(
    modifier: Modifier = Modifier,
    @Size(min = 1)
    contacts: List<Contact>,
    onClick: (Contact) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier,
    ) {
        items(
            items = contacts,
            key = { it.contactId }
        ) { contact ->
            Contact(contact, onClick)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LazyItemScope.Contact(
    contact: Contact,
    onClick: (Contact) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .animateItemPlacement()
            .clickable { onClick(contact) }
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        val initials = contact.initials
        Box(
            modifier = Modifier
                .size(36.dp)
                .background(
                    color = Color(getColor(initials)),
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = initials,
                style = MaterialTheme.typography.body2.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = Color.White,
            )
        }
        Text(
            text = contact.name,
            style = MaterialTheme.typography.body2,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Preview(name = "Contacts light theme")
@Preview(
    name = "Contacts dark theme",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
fun Preview() {
    MdcTheme {
        Contacts(contacts = Samples.contacts)
    }
}