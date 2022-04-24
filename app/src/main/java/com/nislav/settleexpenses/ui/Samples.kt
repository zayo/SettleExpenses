package com.nislav.settleexpenses.ui

import com.nislav.settleexpenses.db.entities.Contact
import com.nislav.settleexpenses.db.entities.ContactWithState
import com.nislav.settleexpenses.db.entities.Expense
import com.nislav.settleexpenses.db.entities.ExpenseWithContacts
import java.text.SimpleDateFormat
import java.util.Date

object Samples {

    private val DATE_FORMAT = SimpleDateFormat.getDateInstance()

    val contacts = listOf(
        Contact("Boris", "MacLeod").apply { contactId = System.currentTimeMillis() + 1 },
        Contact("Nicholas", "Churchill").apply { contactId = System.currentTimeMillis() + 2 },
        Contact("Bella", "Chapman").apply { contactId = System.currentTimeMillis() + 3 },
        Contact("Tim", "Avery").apply { contactId = System.currentTimeMillis() + 4 },
        Contact("Rose", "Lewis").apply { contactId = System.currentTimeMillis() + 5 },
        Contact("Keith", "Marshall").apply { contactId = System.currentTimeMillis() + 6 },
        Contact("Amanda", "Anderson").apply { contactId = System.currentTimeMillis() + 7 },
        Contact("Donna", "Oliver").apply { contactId = System.currentTimeMillis() + 8 },
        Contact("Alexandra", "Harris").apply { contactId = System.currentTimeMillis() + 9 },
        Contact("Abigail", "Smith").apply { contactId = System.currentTimeMillis() + 10 },
        Contact("Isaac", "Hughes").apply { contactId = System.currentTimeMillis() + 11 },
        Contact("Michael", "Fraser").apply { contactId = System.currentTimeMillis() + 12 },
        Contact("Rachel", "Wilkins").apply { contactId = System.currentTimeMillis() + 13 },
        Contact("Keith", "Rampling").apply { contactId = System.currentTimeMillis() + 14 },
        Contact("Ryan", "Bower").apply { contactId = System.currentTimeMillis() + 15 },
        Contact("Mary", "Hardacre").apply { contactId = System.currentTimeMillis() + 16 },
        Contact("Andrew", "Nash").apply { contactId = System.currentTimeMillis() + 17 },
    )

    val expenses = listOf(
        ExpenseWithContacts(
            Expense(
                name = "Friday lunch",
                amount = 500,
                date = DATE_FORMAT.format(Date(System.currentTimeMillis())),
            ).apply {
                expenseId = System.currentTimeMillis() + 1
            },
            contacts = listOf(
                ContactWithState(
                    expenseId = System.currentTimeMillis() + 1,
                    contactId = System.currentTimeMillis() + 1,
                    firstName = "Tim",
                    lastName = "Avery",
                    paid = true
                ),
                ContactWithState(
                    expenseId = System.currentTimeMillis() + 1,
                    contactId = System.currentTimeMillis() + 2,
                    firstName = "Rose",
                    lastName = "Lewis",
                    paid = false
                ),
            )
        ),
        ExpenseWithContacts(
            Expense(
                name = "Saturday dinner",
                amount = 300,
                date = DATE_FORMAT.format(Date(System.currentTimeMillis())),
            ).apply {
                expenseId = System.currentTimeMillis() + 2
            },
            contacts = listOf(
                ContactWithState(
                    expenseId = System.currentTimeMillis() + 2,
                    contactId = System.currentTimeMillis() + 1,
                    firstName = "Tim",
                    lastName = "Avery",
                    paid = true
                ),
            )
        )
    )
}